import sbt.Keys.libraryDependencies

lazy val sparkVersion = "2.0.2"
lazy val scalaLoggingVersion = "3.5.0"
lazy val commonIoVersion = "1.3.2"

lazy val commonSettings = Seq(
  organization := "com.stulsoft.pspark",
  version := "1.1.1",
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "org.apache.spark" %% "spark-core" % sparkVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion
  )
)

resolvers += "Spark Packages Repo" at "http://dl.bintray.com/spark-packages/maven"
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
resolvers += "Repo at github.com/ankurdave/maven-repo" at "https://github.com/ankurdave/maven-repo/raw/master"

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "pspark"
  )
  .dependsOn(util)
  .aggregate(util, mllib, spark_sql, stream)

lazy val util = (project in file("util"))
  .settings(commonSettings: _*)
  .settings(
    name := "util"
  )
  .settings(
    libraryDependencies ++= Seq(
      // https://mvnrepository.com/artifact/org.apache.commons/commons-io
      "org.apache.commons" % "commons-io" % commonIoVersion
    )
  )

lazy val mllib = (project in file("mllib"))
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq("org.apache.spark" %% "spark-mllib" % sparkVersion)
  )
  .settings(
    name := "mllib"
  )
  .dependsOn(util)

lazy val spark_sql = (project in file("spark_sql"))
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq("org.apache.spark" %% "spark-sql" % sparkVersion)
  )
  .settings(
    name := "spark_sql"
  )
  .dependsOn(util)

lazy val stream = (project in file("stream"))
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-streaming" % sparkVersion
    )
  )
  .settings(
    name := "stream"
  )
  .dependsOn(util)