import sbt.Keys.libraryDependencies

lazy val scalatestVersion = "3.0.4"
lazy val scalaMockTestSupportVersion = "3.6.0"
lazy val typeSafeConfVersion = "1.3.2"
lazy val scalaLoggingVersion = "3.7.2"
lazy val logbackClassicVersion = "1.2.3"
lazy val scalaXmlVersion = "1.0.6"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "0.0.2",
  scalaVersion := "2.12.4",
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "org.scala-lang.modules" %% "scala-xml" % scalaXmlVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "ch.qos.logback" % "logback-classic" % logbackClassicVersion,
    "com.typesafe" % "config" % typeSafeConfVersion,
    "org.scalatest" %% "scalatest" % scalatestVersion % "test",
    "org.scalamock" %% "scalamock-scalatest-support" % scalaMockTestSupportVersion % "test"
  )
)

lazy val xml = project.in(file("."))
  .settings(commonSettings)
  .settings(
    name := "xml"
  )

parallelExecution in Test := true
