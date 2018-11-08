import sbt.Keys.libraryDependencies

lazy val akkaVersion = "2.5.17"
lazy val scalatestVersion = "3.0.5"
lazy val scalaLoggingVersion = "3.9.0"
lazy val logbackClassicVersion = "1.2.3"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "0.0.1",
  javacOptions ++= Seq("-source", "11"),
  scalaVersion := "2.12.7",
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "ch.qos.logback" % "logback-classic" % logbackClassicVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
    "org.scalatest" %% "scalatest" % scalatestVersion % "test"
  )
)


lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "queues"
  )
