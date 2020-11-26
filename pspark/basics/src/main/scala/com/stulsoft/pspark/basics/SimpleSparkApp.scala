/*
 * Copyright (c) 2016. Yuriy Stul
 */

package com.stulsoft.pspark.basics

import com.stulsoft.pspark.util.PSparkUtil
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Simple Spark application.
  *
  * From the book Nick Pentreath "Machine Learning with Spark"
  *
  * Created by Yuriy Stul on 11/14/2016.
  */
object SimpleSparkApp extends App {
  println("==>Start")

  val conf = new SparkConf().setAppName("Simple Spark App").setMaster("local[2]")
  val sc = new SparkContext(conf)
  // we take the raw data in CSV format and convert it into a
  // set of records of the form(user, product, price)
  val data = sc.textFile(PSparkUtil.getResourceFilePath("data/UserPurchaseHistory.csv"))
    .map(line => line.split(","))
    .map(purchaseRecord => (purchaseRecord(0),
      purchaseRecord(1), purchaseRecord(2)))
  // let's count the number of purchases
  val numPurchases = data.count()
  // let's count how many unique users made purchases
  val uniqueUsers = data.map { case (user, _, _) => user
  }.distinct().count()
  // let's sum up our total revenue
  val totalRevenue = data.map {
    case (_, _, price) => price.toDouble
  }.sum()
  // let's find our most popular product
  // first we map the data to records of (product, 1)
  // using a PairFunction and the Tuple2 class.
  // then we call a reduceByKey operation with a Function2,
  // which is essentially the sum function
  val productsByPopularity = data
    .map { case (_, product, _) => (product, 1) }
    .reduceByKey(_ + _)
    .collect()
    .sortBy(-_._2)
  val mostPopular = productsByPopularity(0)
  println("Total purchases: " + numPurchases)
  println("Unique users: " + uniqueUsers)
  println("Total revenue: " + totalRevenue)
  println("Most popular product: %s with %d purchases"
    .format(mostPopular._1, mostPopular._2))
  println("==>End")
}
