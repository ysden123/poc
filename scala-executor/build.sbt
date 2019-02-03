import sbt.Keys.scalacOptions

lazy val root = (project in file(".")).
  settings(
    name := "scala-executor",
    version := "1.1.0",
    scalaVersion := "2.12.7",
    javacOptions ++= Seq("-source", "11"),

    libraryDependencies ++= {
      val scalaLoggingVersion = "3.5.0"
      val logbackClassicVersion = "1.1.2"
      val springContextVersion = "5.1.4.RELEASE"
      val akkaVersion = "2.5.20"
      val quartzVersion = "2.2.1"
      Seq(
        "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
        "com.typesafe.akka" %% "akka-actor" % akkaVersion,
        "ch.qos.logback" % "logback-classic" % logbackClassicVersion,
        "org.springframework" % "spring-context" % springContextVersion,
        "org.quartz-scheduler" % "quartz" %quartzVersion,
        "org.quartz-scheduler" % "quartz-jobs" %quartzVersion
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