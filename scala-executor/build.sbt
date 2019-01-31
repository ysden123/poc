import sbt.Keys.scalacOptions

lazy val root = (project in file(".")).
  settings(
    name := "scala-executor",
    version := "1.0.0",
    scalaVersion := "2.12.7",
    javacOptions ++= Seq("-source", "11"),

    libraryDependencies ++= {
      val akkaVersion = "2.4.14"
      Seq(
        "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
        "ch.qos.logback" % "logback-classic" % "1.1.2",
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