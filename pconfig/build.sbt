import sbt.Keys.libraryDependencies

lazy val typeSafeConfVersion = "1.4.0"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "0.0.1",
  scalaVersion := "2.12.4",
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "com.typesafe" % "config" % typeSafeConfVersion
  )
)

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "pconfig"
  )

mainClass in assembly := Some("com.stulsoft.pconfig.Main")
