package com.stulsoft.pspark.spark_sql.json

import com.stulsoft.pspark.util.PSparkUtil
import org.apache.spark.sql.SparkSession

/** Playing with Dataset, JSon
  *
  * @author Yuriy Stul.
  * @see [[https://spark.apache.org/docs/latest/sql-programming-guide.html#overview Spark SQL, DataFrames and Datasets Guide]]
  */
object CreateDSFromJson extends App {
  println("==>main")
  val sparkSession = SparkSession
    .builder()
    .appName("Create DS From Json")
    .master("local[*]")
    .getOrCreate()

  test1(sparkSession)

  sparkSession.close()
  println("<==main")

  def test1(sparkSession: SparkSession): Unit = {
    println("==>test1")
    import sparkSession.implicits._
    val peopleDS = sparkSession.read.json(PSparkUtil.getResourceFilePath("input2.json")).as[Person]
    println("DataSet")
    peopleDS.show()

    println("Schema")
    peopleDS.printSchema()

    println("peopleDS.select(\"age\").show()")
    peopleDS.select("age").show()

    println("Select everybody, but increment the age by 1")
    peopleDS.select($"name", $"age" + 1).show()

    println("Select people older than 21")
    peopleDS.filter($"age" > 21).show()

    println("Count people by age")
    peopleDS.groupBy("age").count().show()

    println("Only defined ages:")
    peopleDS.filter(p => p.age.isDefined).map(p => p.age).show()

    // Register the DataFrame as a SQL temporary view
    peopleDS.createOrReplaceTempView("people")

    val sqlDF = sparkSession.sql("SELECT * FROM people")
    println("SELECT * FROM people")
    sqlDF.show()
    println("<==test1")
  }
}
