import sbt.Keys.libraryDependencies

lazy val scalaTestVersion = "3.0.1"
lazy val scalaLoggingVersion = "3.5.0"
lazy val logbackVersion = "1.1.2"
lazy val jerseyVersion = "1.19.3"
lazy val typeSafeConfVersion = "1.3.1"
lazy val httpClientVersion = "4.5.3"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.12.2",
  ivyScala := ivyScala.value map {
    _.copy(overrideScalaVersion = true)
  },
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "ch.qos.logback" % "logback-classic" % logbackVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "com.typesafe" % "config" % typeSafeConfVersion,
    "org.apache.httpcomponents" % "httpclient" % httpClientVersion,
    "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
  )
)

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

lazy val mailgun4s = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "mailgun4s"
  )