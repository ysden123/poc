lazy val scalaLoggingVersion = "3.9.0"
lazy val logbackVersion = "1.2.3"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "0.1.0",
  scalaVersion := "2.12.7",
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "ch.qos.logback" % "logback-classic" % logbackVersion % "test"
  )
)

lazy val scalaCsvReader = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "logging"
  )