/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.chart

import java.awt.Dimension

import com.stulsoft.chart.util.Utils
import org.jfree.chart.plot.{PlotOrientation, XYPlot}
import org.jfree.chart.renderer.xy.XYShapeRenderer
import org.jfree.chart.{ChartFactory, ChartPanel, JFreeChart}
import org.jfree.data.xy.{XYDataset, XYSeries, XYSeriesCollection}
import org.jfree.ui.{ApplicationFrame, RefineryUtilities}

import scala.io.Source

/**
  * @see [[https://www.tutorialspoint.com/jfreechart/jfreechart_quick_guide.htm AWT Based Application]]
  * @author Yuriy Stul
  */
object XYLineChart_AWT extends App {

  lazy val chart = new XYLineChartAWT("Browser Usage Statistics", "Which Browser are you using?")
  chart.pack()
  RefineryUtilities.centerFrameOnScreen(chart)
  chart.setVisible(true)

  class XYLineChartAWT(applicationTitle: String, chartTitle: String) extends ApplicationFrame(applicationTitle) {
    val xyLineChart: JFreeChart = ChartFactory.createXYLineChart(
      chartTitle,
      "Category",
      "Score",
      createDataSet(),
      PlotOrientation.VERTICAL,
      true,
      true,
      false
    )


    val chartPanel = new ChartPanel(xyLineChart)
    chartPanel.setPreferredSize(new Dimension(560, 367))
    val plot: XYPlot = xyLineChart.getXYPlot

    //    val renderer = new XYLineAndShapeRenderer
    val renderer = new XYShapeRenderer
    //    renderer.setSeriesPaint(0, Color.RED)
    //    renderer.setSeriesPaint(1, Color.GREEN)
    //    renderer.setSeriesPaint(2, Color.YELLOW)
    //    renderer.setSeriesStroke(0, new BasicStroke(4.0f))
    //    renderer.setSeriesStroke(1, new BasicStroke(3.0f))
    //    renderer.setSeriesStroke(2, new BasicStroke(2.0f))

    plot.setRenderer(renderer)
    setContentPane(chartPanel)

    def createDataSet(): XYDataset = {
      val src = Source.fromFile(Utils.getResourceFilePath("browsers.txt"))
      val lines = src.getLines().toList
      src.close()
      val data = lines.map(l => l.split(",").toList).groupBy(x => x.head)

      val dataSet: XYSeriesCollection = new XYSeriesCollection()

      for (d <- data) {
        val xySeries = new XYSeries(d._1)
        for (v <- d._2) {
          xySeries.add(v(1).toDouble, v(2).toDouble)
        }
        dataSet.addSeries(xySeries)
      }
      dataSet
    }
  }

}
