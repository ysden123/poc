package com.stulsoft.course.chapter1

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author Yuriy Stul.
  */
object Chapter1 extends App {
  test1()
  def test1(): Unit = {
    println("==>test1")
    val conf = new SparkConf().setAppName("Simple Spark App").setMaster("local[2]")
    val sc = new SparkContext(conf)
    sc.stop()
    println("<==test1")
  }
}
