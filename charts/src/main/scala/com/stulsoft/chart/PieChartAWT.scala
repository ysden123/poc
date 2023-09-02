package com.stulsoft.chart

import javax.swing.JPanel

import org.jfree.chart.{ChartFactory, ChartPanel, JFreeChart}
import org.jfree.data.general.{DefaultPieDataset, PieDataset}
import org.jfree.chart.ui.{ApplicationFrame, UIUtils}

/**
 * @see [[https://www.tutorialspoint.com/jfreechart/jfreechart_quick_guide.htm AWT Based Application]]
 * @author Yuriy Stul.
 */
object PieChartAWT extends App {

  private val demo = new PieChart_AWT("Mobile sales")

  private class PieChart_AWT(title: String) extends ApplicationFrame(title) {
    setContentPane(createDemoPanel())

    private def createDemoPanel(): JPanel = {
      new ChartPanel(createChart(createDataSet()))
    }

    private def createDataSet(): PieDataset[String] = {
      val dataSet = new DefaultPieDataset[String]()
      dataSet.setValue("IPhone 5s", 20.0)
      dataSet.setValue("Samsung Grand", 20.0)
      dataSet.setValue("MotoG", 40.0)
      dataSet.setValue("Nokia Lumia", 10.0)
      dataSet
    }

    private def createChart(dataSet: PieDataset[String]): JFreeChart = {
      ChartFactory.createPieChart(
        "Mobile Sales", // Chart title
        dataSet, // data
        true, // include legend
        true,
        false
      )
    }
  }

  demo.setSize(560, 367)
  UIUtils.centerFrameOnScreen(demo)
  demo.setVisible(true)

}
