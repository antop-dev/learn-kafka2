plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.kotlin.reflect)
    implementation(libs.kafka.clients)
    implementation(libs.kotlin.logging.jvm)
    implementation(libs.logback.classic)
}
