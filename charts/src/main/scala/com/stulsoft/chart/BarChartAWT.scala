package com.stulsoft.chart

import com.stulsoft.chart.util.Utils

import scala.io.Source

/**
  * @author Yuriy Stul.
  */
object BarChartAWT extends App {
  test()

  def test(): Unit = {
    val src = Source.fromFile(Utils.getResourceFilePath("carsales.csv"))
//    val data = src.getLines().toList.map(line => line.split(",").map(cols => (cols(0), cols(1), cols(2), cols(3), cols(4))))
    val data = src.getLines().toList.map(line => line.split(","))
    val headers=data.take(1)
    val dataSet = data.tail

    println(headers.mkString("|"))
    dataSet.foreach(l=>println(l.mkString("^")))
    src.close()
  }
}
