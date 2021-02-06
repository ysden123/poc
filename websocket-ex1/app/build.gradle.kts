plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
//    mavenCentral()
    // Use JCenter for resolving dependencies.
    jcenter()
}

val vertxVersion = "4.0.2"
val log4j2Version = "2.14.0"
val slf4jVersion = "1.7.30"

dependencies {
    implementation(platform("io.vertx:vertx-stack-depchain:$vertxVersion"))

    implementation("io.vertx:vertx-core")

    // logging
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.slf4j:jcl-over-slf4j:$slf4jVersion")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:$log4j2Version")

    // Test
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.compileJava{
    options.release.set(11)
}

application {
    // Define the main class for the application.
    mainClass.set("com.stulsoft.poc.websocket.ex1.App")
}

tasks.test {
    // Use junit platform for unit tests.
    useJUnitPlatform()
}
