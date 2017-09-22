package com.stulsoft.chart

import com.stulsoft.chart.util.Utils
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.{ChartFactory, ChartPanel, JFreeChart}
import org.jfree.data.category.{CategoryDataset, DefaultCategoryDataset}
import org.jfree.ui.{ApplicationFrame, RefineryUtilities}

import scala.io.Source

/**
  * @see [[https://www.tutorialspoint.com/jfreechart/jfreechart_quick_guide.htm AWT Based Application]]
  * @author Yuriy Stul.
  */
object BarChartAWT extends App {

  val chart: BarChart_AWT = new BarChart_AWT("Car Usage Statistics", "Wich car do you like?")

  class BarChart_AWT(applicationName: String, chartTitle: String) extends ApplicationFrame(applicationName) {
    val barChart: JFreeChart = ChartFactory.createBarChart(
      chartTitle,
      "Category",
      "Score",
      createDataSet(),
      PlotOrientation.VERTICAL,
      true, true, false
    )

    val chartPanel = new ChartPanel(barChart)
    chartPanel.setPreferredSize(new java.awt.Dimension(560, 367))
    setContentPane(chartPanel)

    def createDataSet(): CategoryDataset = {
      val src = Source.fromFile(Utils.getResourceFilePath("carsales.csv"))
      val data = src.getLines().toList.map(line => line.split(","))
      val headers = data.head.tail
      val information = data.tail
      val dataSet: DefaultCategoryDataset = new DefaultCategoryDataset()

      information.foreach(i => (1 to 4).foreach(column => dataSet.addValue(i(column).toDouble, i(0), headers(column - 1))))

      dataSet
    }

  }

  chart.pack()
  RefineryUtilities.centerFrameOnScreen(chart)
  chart.setVisible(true)
}
