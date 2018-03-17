import sbt.Keys.libraryDependencies

lazy val scalaTestVersion = "3.0.1"
lazy val scalaLoggingVersion = "3.5.0"
lazy val logbackVersion = "1.1.2"
lazy val typeSafeConfVersion = "1.3.1"
lazy val quartzVersion = "2.2.3"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.12.4",
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "ch.qos.logback" % "logback-classic" % logbackVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "com.typesafe" % "config" % typeSafeConfVersion,
    "com.typesafe" % "config" % typeSafeConfVersion,
    "org.quartz-scheduler" % "quartz" % quartzVersion,
    "org.quartz-scheduler" % "quartz-jobs" % quartzVersion,
    "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
  )
)

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

lazy val pquartz = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "pquartz"
  )