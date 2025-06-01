import io.confluent.kafka.serializers.KafkaAvroSerializer
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig
import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import student.avro.Student

fun main() {
    AvroProducer { Student("Peter", 1) }.publish()
//    AvroProducer { Student("Peter", "Parker", 1) }.publish()
}

class AvroProducer<T>(
    private val action: () -> T,
) {
    private val log = KotlinLogging.logger { }

    fun publish() {
        // 설정
        val props =
            mapOf(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java.getName(),
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to KafkaAvroSerializer::class.java.getName(),
                KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG to "http://localhost:8081",
            )
        // 프로듀서 생성 및 사용
        KafkaProducer<String, T>(props).use { producer ->
            // GenericRecord를 사용하여 메시지 생성
            val student = action()
            // 메시지 전송
            val record = ProducerRecord<String, T>("peter-avro2", student)
            producer.send(record) { metadata, exception ->
                exception?.run {
                    log.info { "Message deliver failed: ${exception.message}" }
                } ?: run {
                    log.info { "Message delivered to ${metadata.topic()} [${metadata.partition()}], message = ${record.value()}" }
                }
            }
            producer.flush()
        }
    }
}
