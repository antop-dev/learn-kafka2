allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    plugins.withType<JavaPlugin> {
        extensions.configure<JavaPluginExtension> {
            toolchain.languageVersion.set(JavaLanguageVersion.of(17))
        }

        configurations.configureEach {
            if (name == "compileOnly") {
                extendsFrom(configurations.getByName("annotationProcessor"))
            }
        }

        tasks.withType<Test>().configureEach {
            useJUnitPlatform()
        }
    }

    configurations.all {
        resolutionStrategy {
            force(libs.kafka.clients.get())
        }
    }
}
