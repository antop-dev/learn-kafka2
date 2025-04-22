package org.antop.kafka.producer

import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.kafka.clients.producer.Callback
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import java.lang.Exception

class PeterProducerCallback(private val record: ProducerRecord<String, String>) : Callback {
    private val log = KotlinLogging.logger { }

    override fun onCompletion(
        metadata: RecordMetadata,
        exception: Exception?
    ) {
        exception?.run {
            log.error(this) {}
        } ?: run {
            log.info {
                "Topic: ${metadata.topic()}, " +
                        "Partition: ${metadata.partition()}, " +
                        "Offset: ${metadata.offset()}, " +
                        "Received Message: ${record.value()}"
            }
        }
    }
}