/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.scala.chart

import de.sciss.chart.module.Charting

import scala.util.Random

/**
 * @author Yuriy Stul
 */
object BarChartApp  extends App with Charting {
  var random = Random
  val data = for (i <- 0 to 23) yield (i, random.nextInt(1000))

  val chart = XYBarChart(data)

  chart.title = "The title"
  chart.show("Test")
  chart.saveAsPNG("/tmp/chart.png")
}
