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

val logVersion = "2.14.1"
dependencies {
    implementation("com.opencsv:opencsv:5.4")

    //    Logging
    implementation("org.apache.logging.log4j:log4j-core:$logVersion")
    implementation("org.apache.logging.log4j:log4j-api:$logVersion")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:$logVersion")

    // test.
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

application {
    // Define the main class for the application.
    mainClass.set("com.stulsoft.poc.opencsv.App")
}

tasks.test {
    // Use junit platform for unit tests.
    useJUnitPlatform()
}
