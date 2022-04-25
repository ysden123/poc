val springVersion = "2.6.7"

plugins {
    java
    id("org.springframework.boot") version "2.6.7"
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    mavenCentral()
}

dependencies {
    // Use JUnit Jupiter API for testing.
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")

    // Use JUnit Jupiter Engine for testing.
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    // This dependency is used by the application.
    implementation("org.springframework.boot:spring-boot-starter:$springVersion") {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    }
    implementation("org.springframework.boot:spring-boot-starter-log4j2:$springVersion")
}

application {
    // Define the main class for the application.
    mainClass.set("com.stulsoft.poc.gradle.spring.Application")
}

tasks.compileJava {
    options.release.set(17)
}

tasks.test {
    // Use junit platform for unit tests.
    useJUnitPlatform()
}
