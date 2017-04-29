/*
 * Copyright (c) 2016. Yuriy Stul
 */

package com.stulsoft.pspark.basics

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Demonstrates usage of some RDD operations.
  *
  * Created by Yuriy Stul on 11/19/2016.
  */
object PRDD extends App {
  println("==>start")
  val conf = new SparkConf().setAppName("KMeansExample").setMaster("local[*]")
  val sc = new SparkContext(conf)

  val data = Array(
    ("line 1", 21),
    ("line 2", 22),
    ("line 3", 31),
    ("line 4", 41)
  )


  /**
    * an operator used to accumulate results within a partition
    *
    * @param acc previous aggregated value or zero value
    * @param v   an element from partition
    * @return the aggregated value
    */
  def seqOp(acc: Int, v: (String, Int)): Int = {
    acc + v._2
  }

  /**
    * An associative operator used to combine results from different partitions
    *
    * @param acc1 1st aggregated value
    * @param acc2 2nd aggregated value
    * @return aggregated value of the two input values
    */
  def combOp(acc1: Int, acc2: Int): Int = {
    acc1 + acc2
  }

  /**
    * Call aggregate
    *
    * @see [[http://apachesparkbook.blogspot.co.il/2015/11/aggregate-examples.html]]
    * @param data          source data
    * @param partitionSize number of partitions (default is 8)
    * @param zeroVal       initial value (for each non-empty partition and for common result, i.e. result is sum
    *                      of all 2nd values + 1)
    */
  def aggregateTest(data: Array[(String, Int)], partitionSize: Int, zeroVal: Int): Unit = {
    val rdd = if (partitionSize > 0) sc.parallelize(data, partitionSize) else sc.parallelize(data)

    val result = rdd.aggregate(zeroVal)(seqOp, combOp)
    println(s"partition size = ${rdd.partitions.length}, zeroVal = $zeroVal, result = $result")
  }


  /**
    * Is called when a key(in the RDD element) is found for the first time
    * in a given Partition. This method creates an initial
    * value for the accumulator for that key
    *
    * @return the initial value for the accumulator for that key
    */
  def createCombiner: (Int) => (Int, Int) = {
    (mark) => {
      println(s"Create combiner -> $mark")
      (mark, 1)
    }
  }

  /**
    * Is called when the key already has an accumulator
    *
    * @return merged values: _1 + v (sum); _2 + 1 (counter)
    */
  def mergeValue: ((Int, Int), Int) => (Int, Int) = {
    (acc: (Int, Int), v) => {
      println(s"""Merge value : (${acc._1} + $v, ${acc._2} + 1)""")
      (acc._1 + v, acc._2 + 1)
    }
  }

  /**
    * Is called when more that one partition has accumulator for the same key
    *
    * @return merged values: _1 + _1; _2 + _2
    */
  def mergeCombiners: ((Int, Int), (Int, Int)) => (Int, Int) = {
    (acc1: (Int, Int), acc2: (Int, Int)) => {
      println(s"""Merge Combiner : (${acc1._1} + ${acc2._1}, ${acc1._2} + ${acc2._2})""")
      (acc1._1 + acc2._1, acc1._2 + acc2._2)
    }
  }

  /**
    * Demonstrates calculation the average in each subject using combineByKey.
    *
    * @see [[http://apachesparkbook.blogspot.co.il/search/label/a12%7C%20Combiner]]
    */
  def combineByKeyTest(): Unit = {
    println("==>combineByKeyTest")
    val inputRdd = sc.parallelize(Seq(
      ("maths", 50), ("maths", 60),
      ("english", 65),
      ("physics", 66), ("physics", 61), ("physics", 87)),
      1
    )

    val reduced = inputRdd.combineByKey(
      createCombiner,
      mergeValue,
      mergeCombiners
    )
    reduced.collect.foreach(println)

    val result = reduced.mapValues(x => x._1 / x._2.toFloat)
    result.collect.foreach(println)

    println("<==combineByKeyTest")
  }

  def flatMapTest(): Unit = {
    println("==>flatMapTest")
    val data1 = sc.parallelize(Seq("Hello world", "basta"))
    val data2 = data1.flatMap(x => x.split(" "))

    println("data1:")
    data1.foreach(println)

    println("\ndata2:")
    data2.foreach(println)
    println("<==flatMapTest")
  }

  aggregateTest(data, 0, 0) //>> partition size = 8, zeroVal = 0, result = 115
  aggregateTest(data, 1, 0) //>> partition size = 1, zeroVal = 0, result = 115
  aggregateTest(data, 0, 3) //>> partition size = 8, zeroVal = 3, result = 142
  aggregateTest(data, 1, 3) //>> partition size = 1, zeroVal = 3, result = 121
  aggregateTest(data, 3, 3) //>> partition size = 3, zeroVal = 3, result = 127

  combineByKeyTest()

  flatMapTest()
  println("==>end")
}
