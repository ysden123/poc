import sbt.Keys.libraryDependencies

lazy val scalaLoggingVersion = "3.9.2"
lazy val loggingVersion="2.11.2"
lazy val kafkaVersion="2.1.1"

lazy val commonSettings = Seq(
  organization := "com.stulsoft.pkafka",
  version := "2.0.0",
  scalaVersion := "2.12.8",
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

//resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
//resolvers += "Repo at github.com/ankurdave/maven-repo" at "https://github.com/ankurdave/maven-repo/raw/master"

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "kafka-admin"
  )
