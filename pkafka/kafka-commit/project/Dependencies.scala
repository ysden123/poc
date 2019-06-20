import sbt._

object Dependencies {
  lazy val kafka = "org.apache.kafka" % "kafka_2.12" % "2.2.1"
  lazy val typesafeConfig = "com.typesafe" % "config" % "1.3.4"
  
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.8"
}
