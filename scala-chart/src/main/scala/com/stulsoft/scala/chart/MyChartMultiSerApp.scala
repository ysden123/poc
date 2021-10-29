package com.stulsoft.scala.chart

import de.sciss.chart.module.Charting

/**
  * @author Yuriy Stul.
  */
object MyChartMultiSerApp extends App with Charting {
  val names: List[String] = "Series A" :: "Series B" :: Nil
  val data = for {
    name <- names
    series = for (x <- 1 to 5) yield (x, util.Random.nextInt(5))
  } yield name -> series
  val chart = XYLineChart(data)
  chart.title = "The title"
  chart.show("Test")
  chart.saveAsPNG("/tmp/chart.png")
}
