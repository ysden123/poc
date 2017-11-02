import sbt.Keys.libraryDependencies

lazy val akkaVersion = "2.4.17"
lazy val scalatestVersion = "3.0.4"
lazy val scalaMockTestSupportVersion = "3.6.0"
lazy val typeSafeConfVersion = "1.3.2"
lazy val scalaLoggingVersion = "3.7.2"
lazy val logbackClassicVersion = "1.2.3"
lazy val commonsIoVersion = "2.5"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "0.0.2",
  scalaVersion := "2.12.4",
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "ch.qos.logback" % "logback-classic" % logbackClassicVersion,
    "com.typesafe" % "config" % typeSafeConfVersion,
    "commons-io" % "commons-io" % commonsIoVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
    "org.scalatest" %% "scalatest" % scalatestVersion % "test",
    "org.scalamock" %% "scalamock-scalatest-support" % scalaMockTestSupportVersion % "test"
  )
)

lazy val actorStrategyEmulator = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "actor-strategy-emulator"
  )

parallelExecution in Test := true
