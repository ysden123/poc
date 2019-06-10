lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "1.0.2",
  scalaVersion := "2.12.8",
  libraryDependencies ++= {
    val akkaVersion = "2.5.21"
    val akkaHttpVersion = "10.1.7"
    val scalaLoggingVersion = "3.9.2"
    val logbackClassicVersion = "1.2.3"
    lazy val typeSafeConfVersion = "1.3.3"
    lazy val scalaToolsVersion = "1.1.0"
    Seq(
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
      "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
      "ch.qos.logback" % "logback-classic" % logbackClassicVersion,
      "com.typesafe" % "config" % typeSafeConfVersion,
      "com.stulsoft" %% "ys-scala-tools" % scalaToolsVersion
    )
  }
)

lazy val testHttpServer = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "test-http-server"
  )