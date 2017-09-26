package com.stulsoft.scala.chart

import scalax.chart.api._
import scalax.chart.module.Charting

/**
  * @author Yuriy Stul.
  */
object MyChartApp extends App with Charting {
  val data = for (i <- 1 to 5) yield (i, i)
  val chart = XYLineChart(data)
  chart.title = "The title"
  chart.show("Test")
  chart.saveAsPNG("/tmp/chart.png")
}
