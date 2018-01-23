package com.stulsoft.pspark.spark_sql

import org.apache.spark.sql.SparkSession
import com.stulsoft.pspark.util.PSparkUtil

/**
  * Demonstrates usage DataFrame created from case class.
  *
  * Created by Yuriy Stul on 11/26/2016.
  */
object CreateDFFromCaseClass extends App {
  println("==>main")

  case class Data(
                   id: Option[Int], // Nullable value
                   name: String,
                   value: Option[Double] // Nullable value
                 )

  object StringImplicits {

    implicit class StringImprovements(val s: String) {

      import scala.util.control.Exception.catching

      def toIntSafe: Option[Int] = catching(classOf[NumberFormatException]) opt s.toInt

      def toDoubleSafe: Option[Double] = catching(classOf[NumberFormatException]) opt s.toDouble
    }

  }

  val sparkSession = SparkSession
    .builder()
    .appName("Create FD From Tuples")
    .master("local[*]")
    .getOrCreate()

  import StringImplicits._

  def lineToData(line: String): Data = {
    val row = line.split("\t")
    Data(
      row(0).toIntSafe,
      row(1),
      row(2).toDoubleSafe
    )
  }

  def withCaseClass(): Unit = {
    println("==>withCaseClass")

    import sparkSession.implicits._

    val df = sparkSession.sparkContext
      .textFile(PSparkUtil.getResourceFilePath("input1.csv"))
      .map(lineToData)
      .toDF()

    df.printSchema
    df.foreach(row =>
      println(s"""id = ${row.getAs("id")}, name: ${row.getAs("name")}, value = ${row.getAs("value")}""")
    )

    df.select("id","name").foreach(row => println(row))
    df.select("id","name").foreach(row => println(s"""id = ${row.getAs("id")}, name: ${row.getAs("name")}"""))
    println("<==withCaseClass")
  }

  withCaseClass()
  println("<==main")
}
