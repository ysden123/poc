import sbt.Keys.{javacOptions, scalacOptions}

ThisBuild / scalaVersion := "2.13.3"
ThisBuild / organization := "com.stulsoft"
ThisBuild / version := "1.0.1"

lazy val loggingVersion = "2.13.3"
lazy val akkaHttpVersion = "10.2.1"
lazy val akkaVersion = "2.6.10"
lazy val json4sVersion = "3.6.10"
lazy val scalatestVersion = "3.2.2"
lazy val jacksonVersion = "2.11.3"

lazy val app = (project in file("."))
  .settings(
    name := "scala-json",
    libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % jacksonVersion,
    libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion,
    libraryDependencies += "io.argonaut" %% "argonaut" % "6.3.1",
    libraryDependencies += "io.spray" %% "spray-json" % "1.3.5",
    libraryDependencies += "org.json4s" %% "json4s-native" % json4sVersion,
    libraryDependencies += "org.json4s" %% "json4s-jackson" % json4sVersion,
    libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
    libraryDependencies += "org.apache.logging.log4j" % "log4j-api" % loggingVersion,
    libraryDependencies += "org.apache.logging.log4j" % "log4j-core" % loggingVersion,
    libraryDependencies += "org.apache.logging.log4j" % "log4j-slf4j-impl" % loggingVersion,
    libraryDependencies += "org.scalactic" %% "scalactic" % scalatestVersion,
    libraryDependencies += "org.scalatest" %% "scalatest" % scalatestVersion % Test,
    javacOptions ++= Seq("-source", "11"),
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-language:implicitConversions",
      "-language:postfixOps")

  )
