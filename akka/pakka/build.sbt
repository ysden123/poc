lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "1.2.0",
  scalaVersion := "2.12.4",
  libraryDependencies ++= {
    val akkaVersion = "2.4.17"
    val akkaHttpVersion = "10.0.0"
    val scalacticVersion = "3.0.0"
    val scalatestVersion = "3.0.0"
    val scalaLoggingVersion = "3.7.2"
    val logbackClassicVersion = "1.2.3"
    lazy val typeSafeConfVersion = "1.3.2"
    Seq(
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
      "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
      "ch.qos.logback" % "logback-classic" % logbackClassicVersion,
      "com.typesafe" % "config" % typeSafeConfVersion,
      "org.scalactic" %% "scalactic" % scalacticVersion,
      "org.scalatest" %% "scalatest" % scalatestVersion % "test"
    )
  }
)

lazy val pakka = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "pakka"
  )