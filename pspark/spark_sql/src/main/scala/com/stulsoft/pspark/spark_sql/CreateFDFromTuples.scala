package com.stulsoft.pspark.spark_sql

import com.stulsoft.pspark.util.PSparkUtil
import org.apache.spark.sql.SparkSession

/**
  * Demonstrates usage DataFrame created from Tuples.
  *
  * Created by Yuriy Stul on 11/26/2016.
  */
object CreateFDFromTuples extends App {
  println("==>main")

  val sparkSession = SparkSession
    .builder()
    .appName("Create FD From Tuples")
    .master("local[*]")
    .getOrCreate()

  import sparkSession.implicits._

  /**
    * Column access by index
    */
  def noColumnNameDF(): Unit = {
    println("==>noColumnNameDF")
    val df = sparkSession.sparkContext
      .textFile(PSparkUtil.getResourceFilePath("data/input1.csv"))
      .map(line => line.split("\t"))
      .map(x => (x(0).toInt, x(1), x(2).toDouble))
      .toDF

    df.foreach(row => println(s"id = ${row(0)}, name: ${row(1)}, value = ${row(2)}"))

    /* ERROR!
    df.select("id","name").foreach(row => println(row))
    df.select("id","name").foreach(row => println(s"""id = ${row.getAs("id")}, name: ${row.getAs("name")}"""))*/

    println("<==noColumnNameDF")
  }

  /**
    * Column access by name
    */
  def withColumnNameDF(): Unit = {
    println("==>withColumnNameDF")
    val df = sparkSession.sparkContext
      .textFile(PSparkUtil.getResourceFilePath("data/input1.csv"))
      .map(line => line.split("\t"))
      .map(x => (x(0).toInt, x(1), x(2).toDouble))
      .toDF("id", "name", "value")

    df.printSchema

    df.foreach(row => println(s"""id = ${row.getAs("id")}, name: ${row.getAs("name")}, value = ${row.getAs("value")}"""))

    df.select("id", "name").foreach(row => println(row))
    df.select("id", "name").foreach(row => println(s"""id = ${row.getAs("id")}, name: ${row.getAs("name")}"""))

    println("<==withColumnNameDF")
  }

  noColumnNameDF()
  withColumnNameDF()
  println("<==main")
}
