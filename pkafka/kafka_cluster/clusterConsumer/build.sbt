import sbt.Keys.libraryDependencies
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

mainClass in assembly := Some("com.stulsoft.consumer.Consumer")