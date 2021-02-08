plugins {
    id("com.stulsoft.poc.pgradle.java-application-conventions")
}

dependencies {
    implementation(project(":utilities"))
}

application {
    // Define the main class for the application.
    mainClass.set("com.stulsoft.poc.pgradle.app2.App")
}
