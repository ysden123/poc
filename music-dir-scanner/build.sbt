lazy val scalaLoggingVersion = "3.9.0"
lazy val commonIoVersion = "1.3.2"
lazy val scalaTestVersion = "3.2.0-SNAP10"
lazy val scalaCheckVersion = "1.14.0"
lazy val scalaMockVersion = "4.1.0"
lazy val logbackVersion = "1.2.3"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "1.0.0",
  javacOptions ++= Seq("-source", "11"),
  scalaVersion := "2.12.7",
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "ch.qos.logback" % "logback-classic" % logbackVersion,
    "org.scalatest" %% "scalatest" % scalaTestVersion % "test",
    "org.scalacheck" %% "scalacheck" % scalaCheckVersion % "test",
    "org.scalamock" %% "scalamock" % scalaMockVersion % "test"
  )
)

lazy val musicDirScanner = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "music-dir-scanner"
  )