import sbt.Keys.{javacOptions, scalacOptions}

ThisBuild / scalaVersion := "3.2.2"
ThisBuild / organization := "com.stulsoft"
ThisBuild / version := "1.0.2"

lazy val loggingVersion = "2.20.0"
lazy val json4sVersion = "4.0.6"
lazy val scalatestVersion = "3.2.15"
lazy val jacksonVersion = "2.14.2"
lazy val circeVersion = "0.14.5"

lazy val app = (project in file("."))
  .settings(
    name := "scala-json",
    libraryDependencies ++= Seq(
      "com.fasterxml.jackson.core" % "jackson-databind",
      "com.fasterxml.jackson.module" %% "jackson-module-scala"
    ).map(_ % jacksonVersion),

    libraryDependencies += "io.spray" %% "spray-json" % "1.3.6",

    libraryDependencies ++= Seq(
      "org.json4s" %% "json4s-native",
      "org.json4s" %% "json4s-jackson"
    ).map(_ % json4sVersion),
    libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",

    libraryDependencies ++= Seq(
      "org.apache.logging.log4j" % "log4j-api",
      "org.apache.logging.log4j" % "log4j-core",
      "org.apache.logging.log4j" % "log4j-slf4j-impl"
    ).map(_ % loggingVersion),

    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core",
      "io.circe" %% "circe-generic",
      "io.circe" %% "circe-parser"
    ).map(_ % circeVersion),

    javacOptions ++= Seq("-source", "17"),
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-language:implicitConversions",
      "-language:postfixOps")
  )
