import sbt.Keys.libraryDependencies

lazy val scalaLoggingVersion = "3.9.2"
lazy val logbackVersion = "1.1.2"
lazy val typeSafeConfVersion = "1.3.1"
lazy val mongoDbDriver = "4.1.1"
lazy val epollVersion = "4.1.15.Final"
lazy val nettyHandlerVersion = "4.1.15.Final"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.13.4",
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "ch.qos.logback" % "logback-classic" % logbackVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "com.typesafe" % "config" % typeSafeConfVersion,
    "org.mongodb.scala" %% "mongo-scala-driver" % mongoDbDriver,
    "io.netty" % "netty-transport-native-epoll" % epollVersion,
    "io.netty" % "netty-handler" % nettyHandlerVersion
  )
)

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

lazy val pmongodb = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "pmongodb"
  )