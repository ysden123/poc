import sbt.Keys.libraryDependencies

lazy val jzy3dVersion = "1.0.1"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.12.3",
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "org.jzy3d" % "jzy3d-api" % jzy3dVersion
  )
)

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
resolvers += "Jzy3d Releases" at "http://maven.jzy3d.org/releases"

lazy val Jzy3d = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "Jzy3d"
  )