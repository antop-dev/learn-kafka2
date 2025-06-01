import com.github.davidmc24.gradle.plugin.avro.AvroExtension
import com.github.davidmc24.gradle.plugin.avro.GenerateAvroJavaTask

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.avro)
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://packages.confluent.io/maven/")
    }
}

dependencies {
    implementation(libs.kotlin.reflect)
    implementation(libs.kafka.clients)
    implementation(libs.kotlin.logging.jvm)
    implementation(libs.logback.classic)
    implementation(libs.kafka.avro.serializer)
    implementation(libs.kafka.schema.registry.client)
}

configure<AvroExtension> {
    isCreateSetters = false
}

// Avro 생성 소스 디렉토리 설정
sourceSets {
    main {
        java {
            srcDirs("build/generated-avro")
        }
    }
}

// Avro 생성 태스크 설정
tasks.withType<GenerateAvroJavaTask> {
    source("src/main/avro")
    setOutputDir(file("build/generated-avro"))
}
