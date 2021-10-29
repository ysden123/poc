/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.scala.chart

import de.sciss.chart.module.Charting

import scala.util.Random

/**
 * @author Yuriy Stul
 */
object BarChart2App  extends App with Charting {
  val names: List[String] = "Series A" :: Nil
  val data = for {
    name <- names
    series = for (i <- 0 to 23) yield (i, if (i == 9) 0 else util.Random.nextInt(1000))
  } yield name -> series

  val chart = XYBarChart(data)

  chart.title = "The title"
  chart.show("Test")
  chart.saveAsPNG("/tmp/chart.png")
}
