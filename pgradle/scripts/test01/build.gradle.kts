// See: https://docs.gradle.org/current/userguide/tutorial_using_tasks.html
// Run gradle -q hello
tasks.register("hello") {
    doLast {
        println("Hello world!")
    }
}