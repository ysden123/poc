import sbt.Keys.libraryDependencies

lazy val scalaLoggingVersion = "3.7.2"
lazy val loggingVersion="2.8.2"
lazy val kafkaVersion="1.0.0"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "1.0.0",
  scalaVersion := "2.12.4",
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "org.apache.logging.log4j" % "log4j-api" % loggingVersion,
    "org.apache.logging.log4j" % "log4j-core" % loggingVersion,
    "org.apache.logging.log4j" % "log4j-slf4j-impl" % loggingVersion,
    "org.apache.kafka" % "kafka-clients" % kafkaVersion
  )
)

lazy val kafka3 = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "kafka3"
  )

