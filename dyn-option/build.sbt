import sbt.Keys.libraryDependencies

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "1.0.0",
  scalaVersion := "2.12.4",
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps")
)

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "dyn-option"
  )
