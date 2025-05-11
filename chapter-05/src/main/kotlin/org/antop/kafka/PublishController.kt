package org.antop.kafka

import org.springframework.http.MediaType
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class PublishController(
    private val template: KafkaTemplate<String, String>,
) {
    @PostMapping("/publish", produces = [MediaType.APPLICATION_JSON_VALUE])
    @Transactional
    fun publish(
        @RequestBody message: String,
    ): PublishResult {
        val future = template.send("peter-topic04", UUID.randomUUID().toString(), message)
        val result = future.get()
        val record = result.producerRecord
        return PublishResult(
            topic = record.topic(),
            partition = record.partition(),
            key = record.key(),
            value = record.value(),
            timestamp = record.timestamp(),
        )
    }
}
