plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}
group = "com.stulsoft"
version = "2.1.0"

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")

    // This dependency is used by the application.
    implementation("com.google.guava:guava:31.0.1-jre")
}

application {
    // Define the main class for the application.
    mainClass.set("com.stulsoft.pjar.App")
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

tasks.jar{
    manifest{
        attributes(
            "Implementation-Version" to archiveVersion
        )
    }
}