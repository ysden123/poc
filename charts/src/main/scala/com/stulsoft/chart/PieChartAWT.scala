package com.stulsoft.chart

import javax.swing.JPanel

import org.jfree.chart.{ChartFactory, ChartPanel, JFreeChart}
import org.jfree.data.general.{DefaultPieDataset, PieDataset}
import org.jfree.ui.{ApplicationFrame, RefineryUtilities}

/**
  * @see [[https://www.tutorialspoint.com/jfreechart/jfreechart_quick_guide.htm AWT Based Application]]
  * @author Yuriy Stul.
  */
object PieChartAWT extends App {

  val demo = new PieChart_AWT("Mobile sales")

  class PieChart_AWT(title: String) extends ApplicationFrame(title) {
    setContentPane(createDemoPanel())

    def createDemoPanel(): JPanel = {
      new ChartPanel(createChart(createDataSet()))
    }

    def createDataSet(): PieDataset = {
      val dataSet = new DefaultPieDataset()
      dataSet.setValue("IPhone 5s", 20.0)
      dataSet.setValue("Samsung Grand", 20.0)
      dataSet.setValue("MotoG", 40.0)
      dataSet.setValue("Nokia Lumia", 10.0)
      dataSet
    }

    def createChart(dataSet: PieDataset): JFreeChart = {
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
  RefineryUtilities.centerFrameOnScreen(demo)
  demo.setVisible(true)

}
