import sbt.Keys.libraryDependencies

lazy val akkaVersion = "2.4.17"
lazy val scalatestVersion = "3.0.4"

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
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
    "org.scalatest" %% "scalatest" % scalatestVersion % "test"
  )
)

lazy val tdd = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "tdd"
  )

parallelExecution in Test := true
