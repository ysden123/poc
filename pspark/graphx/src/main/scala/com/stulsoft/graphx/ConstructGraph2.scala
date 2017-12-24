/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.graphx

import org.apache.spark._
import org.apache.spark.graphx.{Edge, Graph}

/** Demonstrates creation of Graph
  *
  * Each vertex is tuple of  vertex ID and a property. In this case property is Person class.
  * Edge attribute is Relation class.
  *
  * @author Yuriy Stul
  */
object ConstructGraph2 extends App {
  val conf = new SparkConf().setAppName("ConstructGraph1").setMaster("local[*]")
  val sc = new SparkContext(conf)
  val vertices = sc.parallelize(Array((1L, Person("Homer", 39)),
    (2L, Person("Marge", 39)), (3L, Person("Bart", 12)),
    (4L, Person("Milhouse", 12))))
  val edges = sc.parallelize(Array(Edge(4L, 3L, Relation("friend")),
    Edge(3L, 1L, Relation("father")), Edge(3L, 2L, Relation("mother")),
    Edge(1L, 2L, Relation("marriedTo"))))

  val graph = Graph(vertices, edges)

  println(s"Graph has ${graph.vertices.count} vertices and ${graph.edges.count} edges.")

  println("Vertices:")
  graph.vertices
    .sortBy({ case (i, _) => i })
    .collect
    .foreach({ case (i, p) => println(s"$i $p") })

  println("Edges:")
  graph.edges
    .sortBy(e => e.srcId)
    .collect
    .foreach({ case Edge(s, d, r) => r match {
      case Relation("marriedTo") => println(s"$s $r $d")
      case _ => println(s"$s  is $r of $d")
    }
    })

  sc.stop()
}
