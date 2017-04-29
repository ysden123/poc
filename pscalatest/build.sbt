// Common settings
lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "1.0.1",
  scalaVersion := "2.11.8",
  libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.0",
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"
)

// Integrated test
lazy val integrationTest = (project in file("integrationTest"))
  .settings(commonSettings: _*)
  .settings(
    name := "integrationTest"
  )
  .dependsOn(pscalatest % "compile -> compile; test -> compile")

lazy val integrationTestRef = LocalProject("integrationTest")

// Production
lazy val pscalatest = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "pscalatest"
  )
  .aggregate(integrationTestRef)
