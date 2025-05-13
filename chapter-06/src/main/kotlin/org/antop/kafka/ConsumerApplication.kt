package org.antop.kafka

import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.KafkaListener

@SpringBootApplication
class ConsumerApplication {
    private val log = KotlinLogging.logger { }

    @KafkaListener(id = "", topics = ["peter-test06"])
    fun listen(record: ConsumerRecord<String, String>) {
        log.info {
            "Topic: ${record.topic()}, Partition: ${record.partition()}, Offset: ${record.offset()}, " +
                "Key: ${record.key()}, Received Message: ${record.value()}"
        }
    }
}

fun main(args: Array<String>) {
    runApplication<ConsumerApplication>(*args)
}
