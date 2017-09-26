package com.stulsoft.scala.chart

import scalax.chart.module.Charting

/**
  * @author Yuriy Stul.
  */
object MyChartScatterApp extends App with Charting {
  val data = for (i <- 1 to 5) yield (i,i)
  val chart = XYLineChart(data)
  chart.plot.setRenderer(new org.jfree.chart.renderer.xy.XYLineAndShapeRenderer(false, true))
  chart.show("Scatter")
  chart.saveAsPNG("/tmp/chart.png")
}
