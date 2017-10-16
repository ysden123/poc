import sbt.Keys.libraryDependencies

lazy val scalatestVersion = "3.0.4"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "0.0.1",
  scalaVersion := "2.12.3",
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % scalatestVersion % "test"
  )
)

lazy val chart = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "test-file"
  )