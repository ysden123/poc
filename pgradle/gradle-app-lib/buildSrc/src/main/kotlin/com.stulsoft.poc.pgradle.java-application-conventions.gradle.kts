plugins {
    // Apply the common convention plugin for shared build configuration between library and application projects.
    id("com.stulsoft.poc.pgradle.java-common-conventions")

    // Apply the application plugin to add support for building a CLI application in Java.
    application
}