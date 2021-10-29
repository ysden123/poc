/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.scala.chart

import de.sciss.chart.module.Charting

/**
 * @author Yuriy Stul
 */
object BarChart3App extends App with Charting {

  var data = List(("Series AA", generateSeries()))

  val chart = XYBarChart(data)

  chart.title = "Demo of Distribution by hour"
  chart.show("Distribution by hour")
  chart.saveAsPNG("/tmp/chart.png")

  def generateSeries(): Seq[(Int, Int)] = {
    val series = for {
      i <- 0 to 23
      value = if (i == 9) 0 else util.Random.nextInt(1000)
    } yield i -> value
    series
  }
}
