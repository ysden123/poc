import Dependencies._

ThisBuild / scalaVersion := "2.12.8"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.stulsoft"
ThisBuild / organizationName := "StulSoft"

lazy val root = (project in file("."))
  .settings(
    name := "kafka-commit",
    javacOptions += Seq("-source", "11"),
    libraryDependencies += kafka,
    libraryDependencies += typesafeConfig,
    libraryDependencies += scalaTest % Test
  )
