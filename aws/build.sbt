import sbt.Keys.libraryDependencies

lazy val slf4jVersion = "1.7.25"
lazy val scalaLoggingVersion = "3.5.0"
lazy val loggingVersion="2.7"
lazy val awsLambdaJavaVersion = "1.1.0"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "0.0.1",
  scalaVersion := "2.12.3",
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

parallelExecution in Test := false

mergeStrategy in assembly := {
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case x => MergeStrategy.first
   }
