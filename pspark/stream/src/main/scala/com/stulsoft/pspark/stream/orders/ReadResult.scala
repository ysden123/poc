/*
 * Copyright (c) 2016. Yuriy Stul
 */

package com.stulsoft.pspark.stream.orders

import org.apache.spark.{SparkConf, SparkContext}


/**
  * Reads results
  *
  * @author Yuriy Stul
  */
object ReadResult extends App {
  println("==>ReadResult")
  val conf = new SparkConf().setAppName(appName).setMaster("local[*]")
  val sc = new SparkContext(conf)
  val allCounts = sc.textFile(s"$resultPrefix*$resultSuffix")
  println(s"allCounts.collect().length=${allCounts.collect().length}")
  allCounts.collect().foreach(println)

  println()
  println("Total")
  allCounts.map { l =>
    val s = l.split(",")
    (s(0).substring(1), s(1).substring(0, s(1).length - 1).toInt)
  }
    .reduceByKey(_ + _)
    .foreach(println)

  println("<==ReadResult")
}
