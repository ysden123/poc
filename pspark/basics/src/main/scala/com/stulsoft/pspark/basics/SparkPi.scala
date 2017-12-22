/*
 * Copyright (c) 2016. Yuriy Stul
 */

package com.stulsoft.pspark.basics

import org.apache.spark.{SparkConf, SparkContext}

import scala.math.random


/**
  * See original code: [[https://github.com/bryantravissmith/Spark-Scala-Template/blob/master/src/main/scala/org/apache/spark/examples/SparkPi.scala]]
  * Created by Yuriy Stul on 11/15/2016.
  */
object SparkPi extends App {
  val conf = new SparkConf().setAppName("Spark Pi").setMaster("local[*]")
  val sc = new SparkContext(conf)
  val slices = if (args.length > 0) args(0).toInt else 2
  val n = math.min(1000000L * slices, Int.MaxValue).toInt
  // avoid overflow
  val count = sc.parallelize(1 until n, slices).map { i =>
    val x = random * 2 - 1
    val y = random * 2 - 1
    if (x * x + y * y <= 1) 1 else 0
  }.reduce(_ + _)
  println("Pi is roughly " + 4.0 * count / (n - 1))
  sc.stop()
}
