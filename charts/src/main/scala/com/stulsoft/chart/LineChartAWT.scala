/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.chart

import com.stulsoft.chart.util.Utils
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.{ChartFactory, ChartPanel, JFreeChart}
import org.jfree.data.category.DefaultCategoryDataset
import org.jfree.ui.{ApplicationFrame, RefineryUtilities}

import scala.io.Source

/**
  * @see [[https://www.tutorialspoint.com/jfreechart/jfreechart_quick_guide.htm AWT Based Application]]
  * @author Yuriy Stul
  */
object LineChartAWT extends App {

  val chart: LineChart_AWT = new LineChart_AWT(
    "School Vs Years",
    "Numer of Schools vs years")
  chart.pack()
  RefineryUtilities.centerFrameOnScreen(chart)
  chart.setVisible(true)


  class LineChart_AWT(applicationTitle: String, chartTitle: String) extends ApplicationFrame(applicationTitle) {

    val lineChart: JFreeChart = ChartFactory.createLineChart(
      chartTitle,
      "Years",
      "Number of Schools",
      createDataSet(),
      PlotOrientation.VERTICAL,
      true, true, false
    )
    val chartPanel: ChartPanel = new ChartPanel(lineChart)
    chartPanel.setPreferredSize(new java.awt.Dimension(560, 367))
    setContentPane(chartPanel)


    def createDataSet(): DefaultCategoryDataset = {
      val dataSet: DefaultCategoryDataset = new DefaultCategoryDataset()
      val src = Source.fromFile(Utils.getResourceFilePath("schools.txt"))
      val data = src.getLines().toList.map(l => l.split(","))
      src.close()
      data.foreach(d => {
        dataSet.setValue(d(0).toDouble, "schools", d(1))
      })
      dataSet
    }
  }

}
