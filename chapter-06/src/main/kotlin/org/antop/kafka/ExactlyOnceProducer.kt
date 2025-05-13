package org.antop.kafka

import org.apache.kafka.clients.producer.Callback
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata

fun main() {
    val props =
        mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:10000,localhost:10001,localhost:10002",
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to "org.apache.kafka.common.serialization.StringSerializer",
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to "org.apache.kafka.common.serialization.StringSerializer",
            ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG to true,
            ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION to 5,
            ProducerConfig.RETRIES_CONFIG to 5,
            ProducerConfig.ACKS_CONFIG to "all",
            ProducerConfig.TRANSACTIONAL_ID_CONFIG to "peter-transaction-01",
        )
    KafkaProducer<String, String>(props).use { producer ->
        producer.initTransactions()
        producer.beginTransaction()

        try {
            val record = ProducerRecord<String, String>("peter-test06", "Hello World!")
            producer.send(record, PeterProducerCallback(record))
            producer.flush()
            println("Message sent successfully")
            producer.commitTransaction()
        } catch (e: Exception) {
            producer.abortTransaction()
            e.printStackTrace()
        }
    }
}

class PeterProducerCallback(
    private val record: ProducerRecord<String, String>,
) : Callback {
    override fun onCompletion(
        metadata: RecordMetadata,
        exception: Exception?,
    ) {
        exception?.printStackTrace() ?: run {
            println(
                "Topic: ${metadata.topic()}, Partition: ${metadata.partition()}, Offset: ${metadata.offset()}, " +
                    "Key: ${record.key()}, Received Message: ${record.value()}",
            )
        }
    }
}
