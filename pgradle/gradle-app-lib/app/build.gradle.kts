plugins {
    id("com.stulsoft.poc.pgradle.java-application-conventions")
}

dependencies {
    implementation(project(":utilities"))
}

application {
    // Define the main class for the application.
    mainClass.set("com.stulsoft.poc.pgradle.app.App")
}

val versionSuffix = when (System.getenv("profile")) {
    "stg" -> "RC"
    "prod" -> "RELEASE"
    else -> "SNAPSHOT"
}

project.version = "1.0.1-${versionSuffix}"