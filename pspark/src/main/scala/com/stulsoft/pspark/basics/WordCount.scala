/*
 * Copyright (c) 2016. Yuriy Stul
 */

package com.stulsoft.pspark.basics

import com.stulsoft.pspark.util.PSparkUtil
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Word count
  *
  * See original here: [[http://spark.apache.org/examples.html]]
  * Created by Yuriy Stul on 11/17/2016.
  */
object WordCount extends App {
  println("==>Start")
  val conf = new SparkConf().setAppName("WordCount").setMaster("local[*]")
  val sc = new SparkContext(conf)
  val data = sc.textFile(PSparkUtil.getResourceFilePath("data/word_count.txt"))
  val counts = data.flatMap(line => line.split(" "))
    .map(word => (word, 1))
    .reduceByKey(_ + _)
  counts.foreach(println)

  println("\nsortByKey")
  println("=========")
  counts.sortByKey().collect.map(x => x._1).foreach(println)

  println("\nsortBy _1")
  println("=========")
  counts.sortBy(x => x._1).collect.map(x => x._1).foreach(println)

  println("\nsortBy _2")
  println("=========")
  counts.sortBy(x => x._2).collect.map(x => x._1).foreach(println)
  sc.stop()
  println("==>End")
}
