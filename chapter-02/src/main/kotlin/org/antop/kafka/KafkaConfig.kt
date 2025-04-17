package org.antop.kafka

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.support.converter.JsonMessageConverter

@Configuration
class KafkaConfig {
    @Bean
    fun topic() = NewTopic("peter-overview01", 1, 1.toShort())

    @Bean
    fun converter() = JsonMessageConverter()
}
