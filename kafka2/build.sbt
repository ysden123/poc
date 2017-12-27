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

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
resolvers += "Repo at github.com/ankurdave/maven-repo" at "https://github.com/ankurdave/maven-repo/raw/master"

lazy val consumer = (project in file("consumer"))
  .settings(commonSettings: _*)
  .settings(
    name := "kafka2-consumer"
  )

lazy val producer = (project in file("producer"))
  .settings(commonSettings: _*)
  .settings(
    name := "kafka2-producer"
  )
