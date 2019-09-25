import sbt.Keys.libraryDependencies

lazy val zeebeClientVersion = "0.20.0"
lazy val scalaTestVersion = "3.0.8"
lazy val scalaCheckVersion = "1.14.0"
lazy val scalaLoggingVersion = "3.9.2"
lazy val scalaMockVersion = "4.3.0"
lazy val logbackVersion = "1.2.3"
lazy val configVersion = "1.3.4"
lazy val commonVersion = "0.0.1"
lazy val projectVersion = "0.0.1"
lazy val projectName = "zeebe-start"

lazy val commonSettings = Seq(
  organization := "com.webpals.expenses",
  version := projectVersion,
  javacOptions ++= Seq("-source", "1.8"),
  scalaVersion := "2.13.1",
  scalacOptions ++= Seq(
    "-feature",
    "-deprecation",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "com.typesafe" % "config" % configVersion,
    "io.zeebe" % "zeebe-client-java" % zeebeClientVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "ch.qos.logback" % "logback-classic" % logbackVersion,
    "org.scalatest" %% "scalatest" % scalaTestVersion % "test",
    "org.scalacheck" %% "scalacheck" % scalaCheckVersion % "test",
    "org.scalamock" %% "scalamock" % scalaMockVersion % "test",
    "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
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
  .settings(
    assemblyJarName in assembly := projectName + "-" + projectVersion + ".jar",
    mainClass in assembly := Some("com.stulsoft.zeebe.start.Main")
  )

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
