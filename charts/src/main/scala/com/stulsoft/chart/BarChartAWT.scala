package com.stulsoft.chart

import com.stulsoft.chart.util.Utils
import org.jfree.chart.{ChartFactory, ChartPanel, JFreeChart}
import org.jfree.chart.plot.PlotOrientation
import org.jfree.data.category.{CategoryDataset, DefaultCategoryDataset}
import org.jfree.ui.{ApplicationFrame, RefineryUtilities}

import scala.io.Source

/**
  * @author Yuriy Stul.
  */
object BarChartAWT extends App {

  /*
    test()

    def test(): Unit = {
      val src = Source.fromFile(Utils.getResourceFilePath("carsales.csv"))
      val data = src.getLines().toList.map(line => line.split(","))
      val headers = data.head
      val dataSet = data.tail

      println(headers.mkString("|"))
      dataSet.foreach(l => println(l.mkString("^")))
      src.close()
    }
  */
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

      information.foreach(i => {
        dataSet.addValue(i(1).toDouble, i(0), headers(0))
        dataSet.addValue(i(2).toDouble, i(0), headers(1))
        dataSet.addValue(i(3).toDouble, i(0), headers(2))
        dataSet.addValue(i(4).toDouble, i(0), headers(3))
      })

      dataSet
    }

  }

  chart.pack()
  RefineryUtilities.centerFrameOnScreen(chart)
  chart.setVisible(true)
}
