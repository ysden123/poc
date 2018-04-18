import sbt.Keys.libraryDependencies

lazy val scalaTestVersion = "3.0.1"
lazy val scalaLoggingVersion = "3.5.0"
lazy val logbackVersion = "1.1.2"
lazy val slickVersion = "3.2.3"
lazy val slf4j_nopVersion = "1.6.4"
lazy val h2Version = "1.4.193"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "0.0.1",
  scalaVersion := "2.12.5",
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "com.h2database" % "h2" % h2Version,
    "org.slf4j" % "slf4j-nop" % slf4j_nopVersion,
    "com.typesafe.slick" %% "slick" % slickVersion,
    "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
    //    "ch.qos.logback" % "logback-classic" % logbackVersion,
    //    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
  )
)

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "pslick"
  )