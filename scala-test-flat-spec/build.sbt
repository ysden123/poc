
lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "1.0.0",
  scalaVersion := "2.12.8",
  parallelExecution in ThisBuild := false,
  libraryDependencies ++= {
    lazy val scalaLoggingVersion = "3.9.2"
    lazy val logbackClassicVersion = "1.2.3"
    lazy val typeSafeConfVersion = "1.3.3"
    lazy val scalaTestVersion = "3.2.0-SNAP10"
    lazy val scalaCheckVersion = "1.14.0"
    Seq(
      "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
      "ch.qos.logback" % "logback-classic" % logbackClassicVersion,
      "com.typesafe" % "config" % typeSafeConfVersion,
      "org.scalatest" %% "scalatest" % scalaTestVersion % "test",
      "org.scalacheck" %% "scalacheck" % scalaCheckVersion % "test"
    )
  }
)

lazy val scalatestFlatSpec = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "scala-test-flat-spec"
  )
