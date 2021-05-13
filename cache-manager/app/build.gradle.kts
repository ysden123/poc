/*
 * Copyright (c) 2021. StulSoft
 */

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

project.version = "1.0.0"

val slf4jVersion = "1.7.30"
val log4jSlf4jVersion = "2.14.1"
val vertxVersion = "4.0.3"

dependencies {
    // Use JUnit Jupiter API for testing.
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.1")

    // Use JUnit Jupiter Engine for testing.
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    // Vertx
    implementation("io.vertx:vertx-core:$vertxVersion")

    // Logging
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.slf4j:jcl-over-slf4j:$slf4jVersion")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:$log4jSlf4jVersion")
}

application {
    // Define the main class for the application.
    mainClass.set("com.stulsoft.poc.cache.manager.App")
}

tasks.test {
    // Use junit platform for unit tests.
    useJUnitPlatform()
}
