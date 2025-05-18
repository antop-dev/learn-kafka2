package org.antop.kafka.producer

import org.antop.kafka.producerProps
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord

// 메세지를 보내고 확인하지 않기
fun main() {
    KafkaProducer<String, String>(producerProps).use { producer ->
        val topic = "peter-basic01"
        repeat(3) { i ->
            val value = "Apache Kafka is a distributed streaming platform - $i"
            val record = ProducerRecord<String, String>(topic, value)
            // 리턴값을 무시하므로 메시지가 성공적으로 전송됐는지 알 수 없음
            producer.send(record)
        }
    }
}