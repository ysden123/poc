/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.chart

import java.awt.{Color, Dimension}

import com.stulsoft.chart.util.DataGenerator
import org.jfree.chart.{ChartFactory, ChartPanel}
import org.jfree.data.xy.{XYDataset, XYSeriesCollection}
import org.jfree.ui.{ApplicationFrame, RefineryUtilities}

/**
  * @author Yuriy Stul
  */
object ScatterPlotExample2 extends App {
  lazy val chart = new ScatterPlotExample2AWT("Scatter Chart Example 2", "Boys VS Girls weight comparison chart")
  chart.pack()
  RefineryUtilities.centerFrameOnScreen(chart)
  chart.setVisible(true)

  class ScatterPlotExample2AWT(applicationTitle: String, chartTitle: String) extends ApplicationFrame(applicationTitle) {
    private val dataSet = createDataset()

    private val chart = ChartFactory.createScatterPlot(
      chartTitle,
      "X-Axis", "Y-Axis", dataSet
    )

    private val chartPanel = new ChartPanel(chart)
    chartPanel.setPreferredSize(new Dimension(800, 400))
    private val plot = chart.getPlot
    plot.setBackgroundPaint(new Color(255, 228, 196))
    setContentPane(chartPanel)

    def createDataset(): XYDataset = {
      val dataSet = new XYSeriesCollection()

      dataSet.addSeries(DataGenerator.generateXYSeries("Boys", 70, 130, 100))
      dataSet.addSeries(DataGenerator.generateXYSeries("Girls", 70, 130, 100))
      dataSet.addSeries(DataGenerator.generateXYSeries("Children", 10, 20, 100))

      dataSet
    }
  }

}
