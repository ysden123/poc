/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.chart

import java.awt.{Color, Dimension}

import com.stulsoft.chart.util.DataGenerator
import org.jfree.chart.{ChartFactory, ChartPanel}
import org.jfree.data.xy.{XYDataset, XYSeries, XYSeriesCollection}
import org.jfree.chart.ui.{ApplicationFrame, UIUtils}

/** Scatter Plot
  *
  * @param applicationTitle application name
  * @param chartTitle       chart name
  * @param xAxisName        X-Axis name
  * @param yAxisName        Y-Axis name
  * @param series           collection of series (x, y)
  * @author Yuriy Stul
  */
case class ScatterPlot(applicationTitle: String, chartTitle: String, xAxisName: String, yAxisName: String,
                       series: Seq[XYSeries]) {
  /**
    * Shows the chart
    */
  def show(): Unit = {
    val chart = new ScatterPlotAWT(applicationTitle, chartTitle)
    chart.pack()
    UIUtils.centerFrameOnScreen(chart)
    chart.setVisible(true)
  }

  class ScatterPlotAWT(applicationTitle: String, chartTitle: String) extends ApplicationFrame(applicationTitle) {
    private val chart = ChartFactory.createScatterPlot(
      chartTitle,
      xAxisName, yAxisName, createDataSet()
    )

    private val chartPanel = new ChartPanel(chart)
    chartPanel.setPreferredSize(new Dimension(800, 400))
    private val plot = chart.getPlot
    plot.setBackgroundPaint(new Color(255, 228, 196))
    setContentPane(chartPanel)

    def createDataSet(): XYDataset = {
      val dataSet = new XYSeriesCollection()
      series.foreach(s => dataSet.addSeries(s))
      dataSet
    }
  }

}

/**
  * Test runner
  */
object ScatterPlotTest extends App {
  ScatterPlot("Scatter Chart", "Boys VS Girls weight comparison chart", "X-Axis", "Y-Axis",
    Seq(DataGenerator.generateXYSeries("Boys", 70, 130, 100),
      DataGenerator.generateXYSeries("Girls", 70, 130, 100),
      DataGenerator.generateXYSeries("Children", 10, 20, 100))
  ).show()
}
