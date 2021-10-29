package com.stulsoft.scala.chart

import de.sciss.chart.module.Charting

/**
  * @author Yuriy Stul.
  */
object MyChartScatterApp extends App with Charting {
//  val data = for (i <- 1 to 5) yield (i,i)

  val names: List[String] = "Series A" :: "Series B" :: Nil
  val data = for {
    name <- names
    series = for (x <- 1 to 25) yield (x, util.Random.nextInt(25))
  } yield name -> series

  val chart = XYLineChart(data)
  chart.plot.setRenderer(new org.jfree.chart.renderer.xy.XYLineAndShapeRenderer(false, true))
  chart.show("Scatter")
  chart.saveAsPNG("/tmp/chart.png")
}
