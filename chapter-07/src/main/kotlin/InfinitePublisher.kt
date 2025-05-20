import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import java.util.UUID

fun main() {
    val log = KotlinLogging.logger { }

    val props =
        mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:10000,localhost:10001,localhost:10002",
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to "org.apache.kafka.common.serialization.StringSerializer",
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to "org.apache.kafka.common.serialization.StringSerializer",
        )

    while (true) {
        val random = (1..100).random()
        repeat(random) {
            KafkaProducer<String, String>(props).use { producer ->
                val uuid = UUID.randomUUID().toString()
                val record = ProducerRecord<String, String>("peter-test07", "random message = $uuid")
                producer.send(record) { metadata, exception ->
                    exception?.printStackTrace() ?: run {
                        log.info {
                            "Topic: ${metadata.topic()}, Partition: ${metadata.partition()}, Offset: ${metadata.offset()}, " +
                                "Key: ${record.key()}, Received Message: ${record.value()}"
                        }
                    }
                }
            }
            log.info { "Message sent successfully" }
        }
        Thread.sleep(1000)
    }
}
