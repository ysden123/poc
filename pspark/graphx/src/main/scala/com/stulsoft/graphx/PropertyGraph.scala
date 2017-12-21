package com.stulsoft.graphx

import org.apache.spark._
import org.apache.spark.graphx._
// To make some of the examples work we will also need RDD
import org.apache.spark.rdd.RDD

/**
  * @see [[https://spark.apache.org/docs/latest/graphx-programming-guide.html#getting-started Getting Started]]
  * @author Yuriy Stul.
  */
object PropertyGraph extends App {
  test1()

  def test1(): Unit = {
    println("==>test1")
    val conf = new SparkConf().setAppName("PropertyGraph").setMaster("local[*]")
    val sc = new SparkContext(conf)

    // Create an RDD for the vertices
    val users: RDD[(VertexId, (String, String))] =
      sc.parallelize(Array((3L, ("rxin", "student")), (7L, ("jgonzal", "postdoc")),
        (5L, ("franklin", "prof")), (2L, ("istoica", "prof"))))
    // Create an RDD for edges
    val relationships: RDD[Edge[String]] =
      sc.parallelize(Array(Edge(3L, 7L, "collab"), Edge(5L, 3L, "advisor"),
        Edge(2L, 5L, "colleague"), Edge(5L, 7L, "pi")))
    // Define a default user in case there are relationship with missing user
    val defaultUser = ("John Doe", "Missing")
    // Build the initial Graph
    val graph = Graph(users, relationships, defaultUser)

    // Count all users which are postdocs
    val c1 = graph.vertices.filter { case (id, (name, pos)) => pos == "postdoc" }.count
    // Count all users which are prof
    val c11 = graph.vertices.filter { case (id, (name, pos)) => pos == "prof" }.count
    // Count all the edges where src > dst
    val c2 = graph.edges.filter(e => e.srcId > e.dstId).count
    // Count all the edges where src < dst
    val c3 = graph.edges.filter(e => e.srcId < e.dstId).count

    // Count all the edges where src > dst
    val c4 = graph.edges.filter { case Edge(src, dst, prop) => src > dst }.count

    // Count all the edges where src < dst
    val c5 = graph.edges.filter { case Edge(src, dst, prop) => src < dst }.count
    println(s"c1=$c1, c11=$c11, c2=$c2, c3=$c3, c4=$c4, c5=$c5")

    // Use the triplets view to create an RDD of facts.
    val facts: RDD[String] =
      graph.triplets.map(triplet =>
        triplet.srcAttr._1 + " is the " + triplet.attr + " of " + triplet.dstAttr._1)
    facts.collect.foreach(println(_))

    sc.stop()
    println("<==test1")
  }
}
