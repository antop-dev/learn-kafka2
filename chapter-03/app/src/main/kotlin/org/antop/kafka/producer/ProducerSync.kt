package org.antop.kafka.producer

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import org.antop.kafka.producerProps
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord

fun main() {
    val log: KLogger = KotlinLogging.logger { }

    KafkaProducer<String, String>(producerProps).use { producer ->
        val topic = "peter-basic01"

        repeat(300) { i ->
            val value = "Apache Kafka is a distributed streaming platform - $i"
            val record = ProducerRecord<String, String>(topic, value)
            val metadata = producer.send(record).get()
            log.info {
                "Topic: ${metadata.topic()}, " +
                    "Partition: ${metadata.partition()}, " +
                    "Offset: ${metadata.offset()}, " +
                    "Received Message: ${record.value()}"
            }
        }
    }
}
