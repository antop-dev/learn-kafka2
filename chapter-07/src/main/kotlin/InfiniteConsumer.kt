import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.time.Duration

fun main() {
    val log = KotlinLogging.logger {}

    val props =
        mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:10000,localhost:10001,localhost:10002",
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to "org.apache.kafka.common.serialization.StringDeserializer",
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to "org.apache.kafka.common.serialization.StringDeserializer",
            ConsumerConfig.GROUP_ID_CONFIG to "peter-consumer-7",
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest",
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to "false",
            ConsumerConfig.ISOLATION_LEVEL_CONFIG to "read_committed",
        )

    KafkaConsumer<String, String>(props).use { consumer ->
        consumer.subscribe(listOf("peter-test07"))
        while (true) {
            consumer.poll(Duration.ofSeconds(1)).forEach { record ->
                log.info {
                    "Topic: ${record.topic()}, Partition: ${record.partition()}, Offset: ${record.offset()}, " +
                        "Key: ${record.key()}, Received Message: ${record.value()}"
                }
            }
            consumer.commitAsync()
        }
    }
}
