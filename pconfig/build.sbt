import sbt.Keys.libraryDependencies

lazy val typeSafeConfVersion = "1.3.3"
lazy val vertxVersion = "3.5.3"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "0.0.2",
  scalaVersion := "2.12.7",
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "com.typesafe" % "config" % typeSafeConfVersion,
    "io.vertx" % "vertx-config" % vertxVersion
  )
)

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "pconfig"
  )

mainClass in assembly := Some("com.stulsoft.pconfig.Main")
