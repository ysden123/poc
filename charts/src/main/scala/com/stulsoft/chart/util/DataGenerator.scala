/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.chart.util

import org.jfree.data.xy.XYSeries

import scala.util.Random

/**
  * @author Yuriy Stul
  */
object DataGenerator {
  /**
    * Returns XYSeries
    *
    * @param key    the series's key
    * @param min    minimal value
    * @param max    maximum value
    * @param length the series's length
    * @return the XYSeries
    */
  def generateXYSeries(key: String, min: Double, max: Double, length: Int): XYSeries = {
    val series = new XYSeries(key)

    (1 to length)
      .map(index => {
        (index, min + Random.nextDouble() * (max - min))
      }).foreach(e => series.add(e._1, e._2))

    series
  }
}
