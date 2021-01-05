plugins {
    application
}

repositories {
    jcenter()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

application {
    // Define the main class for the application.
    mainClass.set("com.stulsoft.poc.pgradle.App")

}

val versionSuffix = when (System.getenv("profile")) {
    "stg" -> "RC"
    "prod" -> "RELEASE"
    else -> "SNAPSHOT"
}
project.version = "1.0.0-${versionSuffix}"

tasks.test {
    // Use junit platform for unit tests.
    useJUnitPlatform()
}
