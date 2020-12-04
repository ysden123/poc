import sbt.Keys.libraryDependencies

lazy val scalaTestVersion = "3.2.3"
lazy val scalaLoggingVersion = "3.9.2"
lazy val slickVersion = "3.3.3"
lazy val loggingVersion="2.14.0"
lazy val h2Version = "1.4.200"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "0.1.0",
  scalaVersion := "2.13.4",
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps"),
  javacOptions ++= Seq("-source", "11"),
  libraryDependencies ++= Seq(
    "com.h2database" % "h2" % h2Version,
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "org.apache.logging.log4j" % "log4j-api" % loggingVersion,
    "org.apache.logging.log4j" % "log4j-core" % loggingVersion,
    "org.apache.logging.log4j" % "log4j-slf4j-impl" % loggingVersion,
    "com.typesafe.slick" %% "slick" % slickVersion,
    "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test
  )
)

//resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "pslick"
  )