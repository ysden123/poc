/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.graphx

import org.apache.spark._
import org.apache.spark.graphx.{Edge, Graph}

/** Demonstrates creation of Graph
  *
  * Each vertex is tuple of  vertex ID and a property. In this case property is Person class/
  *
  * @author Yuriy Stul
  */
object ConstructGraph1 extends App {
  val conf = new SparkConf().setAppName("ConstructGraph1").setMaster("local[*]")
  val sc = new SparkContext(conf)
  val vertices = sc.parallelize(Array((1L, Person("Homer", 39)),
    (2L, Person("Marge", 39)), (3L, Person("Bart", 12)),
    (4L, Person("Milhouse", 12))))
  val edges = sc.parallelize(Array(Edge(4L, 3L, "friend"),
    Edge(3L, 1L, "father"), Edge(3L, 2L, "mother"),
    Edge(1L, 2L, "marriedTo")))

  val graph = Graph(vertices, edges)

  sc.stop()
}
