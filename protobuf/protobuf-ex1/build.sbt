import sbt.Keys.libraryDependencies
lazy val scalaLoggingVersion = "3.9.2"
lazy val loggingVersion="2.12.1"
//lazy val scalaPbRuntimeVersion="0.6.7"
lazy val projectVersion = "0.0.1"
lazy val projectName = "protobuf-ex1"

lazy val commonSettings = Seq(
  organization := "com.stulsoft.protobuf.ex1",
  version := projectVersion,
  javacOptions ++= Seq("-source", "11"),
  scalaVersion := "2.12.8",
  scalacOptions ++= Seq(
    "-feature",
    "-deprecation",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
//    "com.thesamet.scalapb" %% "scalapb-runtime" % scalaPbRuntimeVersion % "protobuf",
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "org.apache.logging.log4j" % "log4j-api" % loggingVersion,
    "org.apache.logging.log4j" % "log4j-core" % loggingVersion,
    "org.apache.logging.log4j" % "log4j-slf4j-impl" % loggingVersion,
  ),
  PB.targets in Compile := Seq(
    scalapb.gen() -> (sourceManaged in Compile).value
  )
)

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
resolvers += "typesafe" at "http://repo.typesafe.com/typesafe/releases/"
resolvers += Resolver.jcenterRepo

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := projectName
  )