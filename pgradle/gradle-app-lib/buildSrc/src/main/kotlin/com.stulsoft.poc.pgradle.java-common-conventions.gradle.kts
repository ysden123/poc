plugins {
    // Apply the java Plugin to add support for Java.
    java
}

repositories {
    mavenCentral()
}

dependencies {
/*
    constraints {
        // Define dependency versions as constraints
        implementation("org.apache.commons:commons-text:1.9")
    }
*/

    implementation("joda-time:joda-time:2.10.14")

    // Use JUnit Jupiter API for testing.
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")

    // Use JUnit Jupiter Engine for testing.
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.test {
    // Use junit platform for unit tests.
    useJUnitPlatform()
}

val versionSuffix = when (System.getenv("profile")) {
    "stg" -> "RC"
    "prod" -> "RELEASE"
    else -> "SNAPSHOT"
}
project.version = "1.1.0-${versionSuffix}"

project.ext["commonsLang3Version"] = "3.11"
