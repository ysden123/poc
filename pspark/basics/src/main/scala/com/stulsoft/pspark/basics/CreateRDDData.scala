/*
 * Copyright (c) 2016. Yuriy Stul
 */

package com.stulsoft.pspark.basics

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Loading data from memory.
  *
  * Created by Yuriy Stul on 11/18/2016.
  */
object CreateRDDData extends App {
  println("==>start")
  val conf = new SparkConf().setAppName("KMeansExample").setMaster("local[*]")
  val sc = new SparkContext(conf)

  val memoryData = Array(
    (1, 2, 3, 4),
    (10, 2, 3, 4),
    (1, 20, 3, 4),
    (1, 20, 30, 4)
  )

  val data = sc.parallelize(memoryData)

  // Get first 2 elements
  data.top(2).foreach(println)

  // Calculate amx and min
  val max = sc.parallelize(Array(1, 2, 3, 4, 5, 6)).reduce(Math.max)
  val min = sc.parallelize(Array(1, 2, 3, 4, 5, 6)).reduce(Math.min)
  println(s"minimum = $min, maximum = $max")
  sc.stop()
  println("==>end")
}
