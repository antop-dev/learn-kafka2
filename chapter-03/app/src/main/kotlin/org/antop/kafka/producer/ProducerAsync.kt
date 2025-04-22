package org.antop.kafka.producer

import org.antop.kafka.producerProps
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import java.util.UUID

fun main() {
    KafkaProducer<String, String>(producerProps).use { producer ->
        val topic = "peter-basic01"
        repeat(1000) { i ->
            val value = "Apache Kafka is a distributed streaming platform - $i"
            val key = UUID.randomUUID().toString()
            val record = ProducerRecord(topic, key, value)
            producer.send(record, PeterProducerCallback(record))
        }
    }
}
