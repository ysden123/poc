/*
 * Copyright (c) 2016. Yuriy Stul
 */

package com.stulsoft.pspark.mllib

import com.stulsoft.pspark.util.PSparkUtil
import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Yuriy Stul on 11/16/2016.
  */
object KMeansExample extends App {
  println("==>Start")

  val conf = new SparkConf().setAppName("KMeansExample").setMaster("local[*]")
  val sc = new SparkContext(conf)

  // Load and parse the data
  val data = sc.textFile(PSparkUtil.getResourceFilePath("data/mllib/kmeans_data.txt"))
  val parsedData = data.map(s => Vectors.dense(s.split(',').map(_.toDouble))).cache()

  // Cluster the data into two classes using KMeans
  val numClusters = 2
  val numIterations = 20
  val clusters = KMeans.train(parsedData, numClusters, numIterations)

  // Evaluate clustering by computing Within Set Sum of Squared Errors
  val WSSSE = clusters.computeCost(parsedData)
  println("Within Set Sum of Squared Errors = " + WSSSE)

  println
  println
  println
  clusters.clusterCenters.foreach(println)

  // getting number of points per cluster
  val cluster_ind = clusters.predict(parsedData)
  cluster_ind.collect().foreach(println)
  println("(cluster,item)")
  cluster_ind.collect().zipWithIndex.sorted.foreach(println)

  sc.stop
  println("==>End")
}
