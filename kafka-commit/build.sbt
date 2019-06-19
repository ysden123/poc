import Dependencies._

ThisBuild / scalaVersion     := "2.13.0"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.stulsoft"
ThisBuild / organizationName := "StulSoft"

lazy val root = (project in file("."))
  .settings(
    name := "kafka-commit",
    libraryDependencies += kafka,
    libraryDependencies += typesafeConfig,
    libraryDependencies += scalaTest % Test
  )
