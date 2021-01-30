plugins {
    id("com.stulsoft.poc.pgradle.vertx.java-application-conventions")
}

dependencies {
    implementation(project(":utilities"))
}

val mainVerticleName = "com.stulsoft.poc.pgradle.vertx.app.MainVerticle"

application {
    // Define the main class for the application.
    mainClass.set("io.vertx.core.Launcher")

    applicationDefaultJvmArgs = listOf(mainClass.get(), "run", mainVerticleName)
}


tasks.jar {
    manifest {
        attributes(
            "Main-Verticle" to mainVerticleName
        )
    }
}