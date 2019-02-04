import sbt.Keys.scalacOptions

lazy val root = (project in file(".")).
  settings(
    name := "scala-jmx",
    version := "1.0.0",
    scalaVersion := "2.12.8",
    javacOptions ++= Seq("-source", "11"),

    libraryDependencies ++= {
      val scalaLoggingVersion = "3.5.0"
      val logbackClassicVersion = "1.1.2"
      Seq(
        "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
        "ch.qos.logback" % "logback-classic" % logbackClassicVersion
      )
    },
    scalacOptions in(Compile, doc) ++= Seq("-author"),
    scalacOptions ++= Seq(
      "-deprecation",
      "-feature",
      "-language:implicitConversions",
      "-language:postfixOps"
      )
  )