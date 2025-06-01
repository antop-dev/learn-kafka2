import io.confluent.kafka.serializers.KafkaAvroDeserializer
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig
import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import student.avro.Student
import java.time.Duration

fun main() {
    AvroConsumer<Student>().subscribe()
}

class AvroConsumer<T> {
    fun subscribe() {
        val log = KotlinLogging.logger {}
        // 설정값
        val props =
            mapOf(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java.getName(),
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to KafkaAvroDeserializer::class.java.getName(),
                ConsumerConfig.GROUP_ID_CONFIG to "peter-avro-consumer1",
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest",
                ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to false,
                KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG to "http://localhost:8081",
                KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG to true,
            )
        // 컨슈머 생성
        KafkaConsumer<String, T>(props).use { consumer ->
            // 토픽 구독
            consumer.subscribe(listOf("peter-avro2"))
            // 대기
            while (true) {
                consumer.poll(Duration.ofMillis(100)).forEach { record ->
                    log.info { record.value() }
                }
                consumer.commitAsync()
            }
        }
    }
}
