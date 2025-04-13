package org.antop.kafka

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class Consumer {
    private val log = KotlinLogging.logger { }

    @KafkaListener(id = "", topics = ["peter-overview01"])
    fun listen(foo: Foo) {
        log.info { "Received: $foo" }
    }
}
