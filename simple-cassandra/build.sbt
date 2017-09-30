import sbt.Keys.libraryDependencies

lazy val cassandraDriverCoreVersion = "3.3.0"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "0.0.1",
  scalaVersion := "2.12.3",
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "com.datastax.cassandra" % "cassandra-driver-core"  % cassandraDriverCoreVersion
  )
)

lazy val simpleCassandra = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "simple-cassandra"
  )