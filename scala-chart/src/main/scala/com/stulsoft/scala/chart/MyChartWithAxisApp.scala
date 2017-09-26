package com.stulsoft.scala.chart

import com.stulsoft.scala.chart.MyChartMultiSerApp.names

import scalax.chart.module.Charting

/**
  * @author Yuriy Stul.
  */
object MyChartWithAxisApp extends App with Charting {
  test1()
  test2()
  def test1(): Unit ={
    val names=Seq("test1")
    val data = for {
      name <- names
      series = for (x <- 1 to 5) yield (x, util.Random.nextInt(5))
    } yield name -> series
    val chart = XYLineChart(data)
    chart.title = "The title 1"
    chart.show("Test1")
  }

  def test2(): Unit ={
    val series = for (x <- 1 to 5) yield (x, util.Random.nextInt(5))
//    val data = Seq(("test2", series))
    val data = Seq("test2" -> series)
    val chart = XYLineChart(data)
    chart.title = "The title 2"
    chart.show("Test2")
  }
}
