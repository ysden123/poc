import sbt.Keys.libraryDependencies

lazy val akkaVersion = "2.5.3"
lazy val scalatestVersion = "3.0.1"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "1.0.0",
  scalaVersion := "2.12.2",
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

lazy val mailOffice = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "mailOffice"
  )