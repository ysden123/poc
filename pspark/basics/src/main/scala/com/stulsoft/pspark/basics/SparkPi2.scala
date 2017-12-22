/*
 * Copyright (c) 2016. Yuriy Stul
 */

package com.stulsoft.pspark.basics

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Calculation of Pi
  * Created by Yuriy Stul on 11/16/2016.
  */
object SparkPi2 extends App {
  val conf = new SparkConf().setAppName("Spark Pi 2").setMaster("local[*]")
  val sc = new SparkContext(conf)
  val slices = if (args.length > 0) args(0).toInt else 2
//  val slices = 1000000
  val count = sc.parallelize(1 to slices)
    .map { i =>
      val x = Math.random
      val y = Math.random
      if (x * x + y * y < 1) 1 else 0
    }.reduce(_ + _)
  println(s"Pi is roughly ${4.0 * count / slices}" )
  sc.stop()
}
