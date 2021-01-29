plugins {
    id("com.stulsoft.poc.pgradle.java-library-conventions")
}

dependencies {
    api(project(":list"))
    implementation("org.apache.commons:commons-lang3:${project.ext["commonsLang3Version"]}")
}