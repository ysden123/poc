import sbt.Keys.libraryDependencies

lazy val scalatestVersion = "3.0.4"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.12.3",
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "org.scalatest" % "scalatest_2.12" % scalatestVersion % "test"
  )
)

lazy val ysTypeInfo = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "ys-type-info"
  )

parallelExecution in Test := true