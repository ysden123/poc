/*
 * Copyright (c) 2020. Yuriy Stul
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    application
}
group = "com.stulsoft.plogz"
version = "1.0.0"

repositories {
    mavenCentral()
}

val logVersion = "2.13.3"

dependencies {
    testImplementation(kotlin("test-junit"))
//    Logging
    implementation("org.apache.logging.log4j:log4j-core:$logVersion")
    implementation("org.apache.logging.log4j:log4j-api:$logVersion")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:$logVersion")
//    Logz.IO
    implementation("io.logz.log4j2:logzio-log4j2-appender:1.0.12")
}
tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}
/*
application {
    mainClassName = "com.stulsoft.plogz.logz1.MainKt"
}*/
