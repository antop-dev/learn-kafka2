package org.antop.kafka

data class PublishResult(
    val topic: String,
    val partition: Int?,
    val key: String?,
    val value: String,
    val timestamp: Long?,
)
