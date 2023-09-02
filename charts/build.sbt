import sbt.Keys.libraryDependencies

lazy val jFreeChartVersion = "1.5.4"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "0.0.2",
  scalaVersion := "3.3.0",
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "org.jfree" % "jfreechart" % jFreeChartVersion
  )
)


lazy val chart = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "chart"
  )