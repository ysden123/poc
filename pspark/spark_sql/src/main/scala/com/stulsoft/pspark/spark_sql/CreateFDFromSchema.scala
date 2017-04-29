package com.stulsoft.pspark.spark_sql

import com.stulsoft.pspark.util.PSparkUtil
import org.apache.spark.sql.types._
import org.apache.spark.sql.{Row, SparkSession}

/**
  * Demonstrates usage DataFrame created from schema.
  *
  * Created by Yuriy Stul on 11/26/2016.
  */
object CreateFDFromSchema extends App {
  println("==>main")

  val NULLABLE = true

  val dataSchema = StructType(Seq(
    StructField("id", IntegerType, NULLABLE),
    StructField("name", StringType, NULLABLE),
    StructField("value", DoubleType, NULLABLE)
  ))

  object StringImplicits {

    implicit class StringImprovements(val s: String) {

      import scala.util.control.Exception.catching

      def toIntSafe: Option[Int] = catching(classOf[NumberFormatException]) opt s.toInt

      def toDoubleSafe: Option[Double] = catching(classOf[NumberFormatException]) opt s.toDouble
    }

  }

  import StringImplicits._

  def lineToData(line: String): Row = {
    val row = line.split("\t")
    Row(row(0).toIntSafe.orNull,
      row(1),
      row(2).toDoubleSafe.orNull)
  }

  val sparkSession = SparkSession
    .builder()
    .appName("Create FD From Tuples")
    .master("local[*]")
    .getOrCreate()

  val sqlContext = sparkSession.sqlContext
  import sqlContext.implicits._

  def withSchema(): Unit = {
    println("==>withSchema")
    val sc = sparkSession.sparkContext
    val rowRDD = sc
      .textFile(PSparkUtil.getResourceFilePath("data/input1.csv"))
      .map(lineToData)
    val df = sparkSession.createDataFrame(rowRDD, dataSchema)

    df.printSchema()
    df.foreach(row =>
      println(s"""id = ${row.getAs("id")}, name: ${row.getAs("name")}, value = ${row.getAs("value")}""")
    )

    df.select("id", "name").foreach(row => println(row))
    df.select("id", "name").foreach(row => println(s"""id = ${row.getAs("id")}, name: ${row.getAs("name")}"""))

    df.select("id", "name")
      .filter('name contains "yur")
      .foreach(row => println(s"""id = ${row.getAs("id")}, name: ${row.getAs("name")}"""))

    df.show()

    println("<==withSchema")
  }

  withSchema()
  println("<==main")
}
