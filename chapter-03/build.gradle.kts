plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.kafka.clients)
    implementation(libs.logback.classic)
    implementation(libs.kotlin.logging.jvm)
}
