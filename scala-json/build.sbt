import sbt.Keys.{javacOptions, scalacOptions}

ThisBuild / scalaVersion := "3.2.2"
ThisBuild / organization := "com.stulsoft"
ThisBuild / version := "1.0.2"

lazy val loggingVersion = "2.20.0"
lazy val json4sVersion = "4.0.6"
lazy val scalatestVersion = "3.2.15"
lazy val jacksonVersion = "2.14.2"

lazy val app = (project in file("."))
  .settings(
    name := "scala-json",
    libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % jacksonVersion,
    libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion,
    libraryDependencies += "io.spray" %% "spray-json" % "1.3.6",
    libraryDependencies += "org.json4s" %% "json4s-native" % json4sVersion,
    libraryDependencies += "org.json4s" %% "json4s-jackson" % json4sVersion,
    libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
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
