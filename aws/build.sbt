import sbt.Keys.libraryDependencies

lazy val scalaLoggingVersion = "3.9.2"
lazy val loggingVersion = "2.12.0"
lazy val awsLambdaJavaVersion = "1.2.0"
lazy val jacksonModuleScalaVersion = "2.9.9"
lazy val scalatestVersion = "3.0.8"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "0.0.2",
  scalaVersion := "2.13.0",
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "org.apache.logging.log4j" % "log4j-api" % loggingVersion,
    "org.apache.logging.log4j" % "log4j-core" % loggingVersion,
    "org.apache.logging.log4j" % "log4j-slf4j-impl" % loggingVersion,
    "com.amazonaws" % "aws-lambda-java-core" % awsLambdaJavaVersion,
    "com.amazonaws" % "aws-lambda-java-events" % awsLambdaJavaVersion
  )
)

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
resolvers += "Repo at github.com/ankurdave/maven-repo" at "https://github.com/ankurdave/maven-repo/raw/master"

lazy val test1 = (project in file("test1"))
  .settings(commonSettings: _*)
  .settings(
    name := "test1"
  )

lazy val test2 = (project in file("test2"))
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonModuleScalaVersion,
      "org.scalatest" %% "scalatest" % scalatestVersion % "test"
    )
  )
  .settings(
    name := "test2"
  )

parallelExecution in Test := false

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
