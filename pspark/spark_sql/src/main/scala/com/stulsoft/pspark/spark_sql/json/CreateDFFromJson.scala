package com.stulsoft.pspark.spark_sql.json

import com.stulsoft.pspark.util.PSparkUtil
import org.apache.spark.sql.SparkSession

/** Playing with DataFrame from JSon
  *
  * @author Yuriy Stul.
  */
object CreateDFFromJson extends App {
  println("==>main")
  val sparkSession = SparkSession
    .builder()
    .appName("Create DF From Json")
    .master("local[*]")
    .getOrCreate()

  test1(sparkSession)

  sparkSession.close()
  println("<==main")


  def test1(sparkSession: SparkSession): Unit = {
    println("==>test1")
    import sparkSession.implicits._

    try {
      val df = sparkSession.read.json(PSparkUtil.getResourceFilePath("input2.json"))
      println("df.show()")
      df.show()

      println("df.printSchema()")
      df.printSchema()

      println("df.select(\"name\").show()")
      df.select("name").show()

      println("Select everybody, but increment the age by 1")
      df.select($"name", $"age" + 1).show()

      println("Select people older than 21")
      df.filter($"age" > 21).show()

      println("Count people by age")
      df.groupBy("age").count().show()
    }
    catch {
      case e: Throwable => println(s"Error: ${e.getMessage}")
    }
    println("<==test1")
  }
}
