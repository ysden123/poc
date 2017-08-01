import sbt.Keys.libraryDependencies

lazy val scalaLoggingVersion = "3.5.0"
lazy val loggingVersion="2.7"
lazy val kafkaVersion="0.10.2.0"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "1.0.0",
  scalaVersion := "2.12.2",
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

lazy val clusterConsumer = (project in file("clusterConsumer"))
  .settings(commonSettings: _*)
  .settings(
    name := "clusterConsumer"
  )

lazy val clusterProducer = (project in file("clusterProducer"))
  .settings(commonSettings: _*)
  .settings(
    name := "clusterProducer"
  )
