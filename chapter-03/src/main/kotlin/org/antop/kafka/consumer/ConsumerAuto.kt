package org.antop.kafka.consumer

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import org.antop.kafka.consumerProps
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.time.Duration

fun main() {
    val log: KLogger = KotlinLogging.logger { }
    val props = consumerProps + mapOf("enable.auto.commit" to true)

    KafkaConsumer<String, String>(props).use { consumer ->
        consumer.subscribe(listOf("peter-basic01"))

        while (true) {
            val records = consumer.poll(Duration.ofSeconds(1))
            records.forEach { record ->
                log.info {
                    "Topic: ${record.topic()}, " +
                        "Partition: ${record.partition()}, " +
                        "Offset: ${record.offset()}, " +
                        "Received Message: ${record.value()}"
                }
            }
        }
    }
}
