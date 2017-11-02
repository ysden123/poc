import sbt.Keys.libraryDependencies

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "0.0.1",
  scalaVersion := "2.12.4",
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps")
)

lazy val serialization = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "serialization"
  )