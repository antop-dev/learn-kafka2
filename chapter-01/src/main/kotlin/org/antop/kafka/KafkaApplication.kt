package org.antop.kafka

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Chapter01Application

fun main(args: Array<String>) {
    runApplication<Chapter01Application>(*args)
}
