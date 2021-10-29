import sbt.Keys.libraryDependencies

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.13.6",
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "de.sciss" %% "scala-chart" % "0.8.0",
    "com.itextpdf" % "itextpdf" % "5.5.6",
    "org.jfree" % "jfreesvg" % "3.0"
  )
)

//resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

lazy val scalaChart = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "scala-chart"
  )