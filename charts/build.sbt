import sbt.Keys.libraryDependencies

lazy val jFreeChartVersion = "1.0.19"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.12.3",
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "org.jfree" % "jfreechart" % jFreeChartVersion
  )
)

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

lazy val chart = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "chart"
  )