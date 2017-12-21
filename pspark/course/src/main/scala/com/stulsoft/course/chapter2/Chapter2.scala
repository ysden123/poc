package com.stulsoft.course.chapter2

import java.net.URL

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author Yuriy Stul.
  */
object Chapter2 extends App {
  val conf = new SparkConf().setAppName("Simple Spark App").setMaster("local[2]")
    .set("spark.eventLog.enabled","true")
    .set("spark.eventLog.dir","file:///c:/tmp/spark-events")
  val sc = new SparkContext(conf)
  test1(sc)
  test2(sc)
  test3(sc)

  def getHost(line: String): String = {
    try {
      new URL(line).getHost
    }
    catch {
      case _: Throwable =>
        println("ERROR:" + line)
      ""
    }

  }

  def test1(sc: SparkContext): Unit = {
    println("==>test1")
    val websitesDay1 = sc.textFile("c:\\work\\chapter2\\websites_day_1.csv")
    val hosts = websitesDay1.map(getHost).collect
    hosts.take(20).foreach(println)
    println(websitesDay1.map(getHost).count)
    println("<==test1")
  }

  def test2(sc: SparkContext): Unit = {
    println("==>test2")
    val websitesDay1 = sc.textFile("c:\\work\\chapter2\\websites_day_1.csv")
    val hosts = websitesDay1.map(getHost).distinct.collect
    hosts.take(20).foreach(println)
    println(websitesDay1.map(getHost).distinct.count)
    println("<==test2")
  }

  def test3(sc: SparkContext): Unit = {
    println("==>test3")
    val websitesDay1 = sc.textFile("c:\\work\\chapter2\\websites_day_2.csv")
    val hosts = websitesDay1.map(getHost).collect
    hosts.take(20).foreach(println)
    println(websitesDay1.map(getHost).count)
    println("<==test3")
  }

  sc.stop()
}
