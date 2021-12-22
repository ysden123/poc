lazy val scalaLoggingVersion = "3.9.4"
lazy val scalaTestVersion = "3.2.10"
lazy val scalaCheckVersion = "1.15.4"
lazy val logbackVersion = "1.2.9"
lazy val catsVersion = "2.7.0"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "1.0.1",
  scalaVersion := "2.13.7",
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "org.typelevel" %% "cats-core" % catsVersion,
      "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "ch.qos.logback" % "logback-classic" % logbackVersion,
    "org.scalatest" %% "scalatest" % scalaTestVersion % "test",
    "org.scalacheck" %% "scalacheck" % scalaCheckVersion % "test"
  )
)

lazy val catsExamples = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "cats-examples"
  )