# 2. 카프카 환경 구성

### 카프카 설치

카프카 설치 (각가 옵션 뭔지 모름 -_-/)

```
docker run -d  \
  --name kafka-local \
  -p 9092:9092 \
  -p 9093:9093 \
  -e KAFKA_NODE_ID=1 \
  -e KAFKA_PROCESS_ROLES=broker,controller \
  -e KAFKA_LISTENERS=PLAINTEXT://:9092,CONTROLLER://:9093 \
  -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 \
  -e KAFKA_CONTROLLER_LISTENER_NAMES=CONTROLLER \
  -e KAFKA_LISTENER_SECURITY_PROTOCOL_MAP=CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT \
  -e KAFKA_CONTROLLER_QUORUM_VOTERS=1@localhost:9093 \
  -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 \
  -e KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR=1 \
  -e KAFKA_TRANSACTION_STATE_LOG_MIN_ISR=1 \
  -e KAFKA_GROUP_INITIAL_REBALANCE_DELAY_MS=0 \
  -e KAFKA_NUM_PARTITIONS=3 \
  apache/kafka:latest
```

토픽<sup>`Topic`</sup> 생성

```
/opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --topic peter-overview01
```

컨슈머<sup>`Consumer`</sup> 실행

```
/opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic peter-overview01
```

프로듀서<sup>`Producer`</sup> 실행

```
/opt/kafka/bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic peter-overview01
```

### 메시지 보내고 받기

[Spring for Apache Kafka - Sample 1](https://github.com/spring-projects/spring-kafka/tree/main/samples/sample-01) 참조하여 프로듀서/컨슈머 개발
