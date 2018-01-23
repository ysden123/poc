package com.stulsoft.pspark.spark_sql.json

import com.stulsoft.pspark.util.PSparkUtil
import org.apache.spark.sql.{SaveMode, SparkSession}

/** Playing with parquet
  *
  * @author Yuriy Stul.
  * @see [[http://parquet.apache.org/documentation/latest/ Parquet]]
  * @see [[https://spark.apache.org/docs/latest/sql-programming-guide.html#manually-specifying-options Parquet]]
  */
object PParquet extends App {
  println("==>main")
  val sparkSession = SparkSession
    .builder()
    .appName("Create DS From Json")
    .master("local[*]")
    .getOrCreate()

  test1(sparkSession)

  sparkSession.close()
  println("<==main")

  def test1(sparkSession: SparkSession): Unit ={
    println("==>test1")
    val peopleDF = sparkSession.read.format("json").load(PSparkUtil.getResourceFilePath("input2.json"))
    peopleDF.select("name", "age").write.mode(SaveMode.Overwrite).format("parquet").save("target/namesAndAges.parquet")

    val sqlDF = sparkSession.sql("SELECT * FROM parquet.`target/namesAndAges.parquet`")
    println("From parquet")
    sqlDF.show()

    println("From parquet - only name")
    sparkSession.sql("SELECT name FROM parquet.`target/namesAndAges.parquet`").show()

    println("From parquet - only non-nullable ages")
    sparkSession.sql("SELECT age FROM parquet.`target/namesAndAges.parquet` WHERE age IS NOT NULL").show()
    println("<==test1")
  }
}
