package org.antop.kafka

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ProducerController(
    private val template: KafkaTemplate<Any, Any>,
) {
    @PostMapping(path = ["/send/foo/{what}"])
    fun sendFoo(
        @PathVariable what: String,
    ): Foo {
        val foo = Foo(what)
        template.send("peter-overview01", foo)
        return foo
    }
}
