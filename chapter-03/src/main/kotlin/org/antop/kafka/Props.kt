package org.antop.kafka

val commonProps =
    mapOf(
        "bootstrap.servers" to "localhost:10000,localhost:10001,localhost:10002",
        "compression.type" to "gzip",
    )

val producerProps =
    commonProps +
        mapOf(
            "key.serializer" to "org.apache.kafka.common.serialization.StringSerializer",
            "value.serializer" to "org.apache.kafka.common.serialization.StringSerializer",
        )

val consumerProps =
    commonProps +
        mapOf(
            "group.id" to "peter-consumer",
            // 컨슈머 오프셋을 찾지 못하는 경우 lstest로 초기화하며 가장 최근부터 메시지를 가져옴
            "auto.offset.reset" to "latest",
            "key.deserializer" to "org.apache.kafka.common.serialization.StringDeserializer",
            "value.deserializer" to "org.apache.kafka.common.serialization.StringDeserializer",
        )
