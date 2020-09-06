import sbt.Keys.libraryDependencies

lazy val scalaLoggingVersion = "3.9.2"
lazy val loggingVersion = "2.13.3"
lazy val json4sVersion = "3.6.9"
lazy val scalaXmlVersion = "1.3.0"
lazy val projectVersion = "1.0.0"
lazy val projectName = "json-path-2-attr"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := projectVersion,
  javacOptions ++= Seq("-source", "11"),
  scalaVersion := "2.13.1",
  scalacOptions ++= Seq(
    "-feature",
    "-deprecation",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "org.json4s" %% "json4s-native" % json4sVersion,
    "org.json4s" %% "json4s-jackson" % json4sVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "org.apache.logging.log4j" % "log4j-api" % loggingVersion,
    "org.apache.logging.log4j" % "log4j-core" % loggingVersion,
    "org.apache.logging.log4j" % "log4j-slf4j-impl" % loggingVersion,
    "org.scala-lang.modules" %% "scala-xml" % scalaXmlVersion
  )
)

//resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
//resolvers += "typesafe" at "http://repo.typesafe.com/typesafe/releases/"
//resolvers += Resolver.jcenterRepo

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := projectName
  )