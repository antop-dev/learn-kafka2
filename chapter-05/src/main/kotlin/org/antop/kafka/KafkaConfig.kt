package org.antop.kafka

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.support.converter.JsonMessageConverter

@Configuration
class KafkaConfig {
    @Bean
    fun topic() = NewTopic("peter-topic04", 1, 3.toShort())

    @Bean
    fun converter() = JsonMessageConverter()
}
