import sbt.Keys.libraryDependencies

lazy val scalaChartVersion = "0.5.1"
lazy val itextpdfVersion = "5.5.10"
lazy val jfreesvgVersion = "3.2"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.12.3",
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "com.github.wookietreiber" %% "scala-chart" % scalaChartVersion,
    "com.itextpdf" % "itextpdf" % itextpdfVersion,
    "org.jfree" % "jfreesvg" % jfreesvgVersion
  )
)

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

lazy val scalaChart = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "scala-chart"
  )