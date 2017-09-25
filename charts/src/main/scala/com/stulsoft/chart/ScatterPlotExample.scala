package com.stulsoft.chart

import java.awt.Color
import javax.swing.{JFrame, SwingUtilities, WindowConstants}

import org.jfree.chart.{ChartFactory, ChartPanel, JFreeChart}
import org.jfree.data.xy.{XYDataset, XYSeries, XYSeriesCollection}

/**
  * @see [[https://www.boraji.com/jfreechart-scatter-chart-example JFreeChart - Scatter Chart example]]
  * @author Yuriy Stul.
  * @author imssbora
  */
class ScatterPlotExample(title: String) extends JFrame(title) {
  // Create data set
  private val dataSet: XYDataset = createDataset()

  // Create chart
  private val chart: JFreeChart = ChartFactory.createScatterPlot(
    "Boys VS Girls weight comparison chart",
    "X-Axis", "Y-Axis", dataSet
  )

  //Changes background color
  private val plot = chart.getPlot
  plot.setBackgroundPaint(new Color(255, 228, 196))

  // Create panel
  private val panel = new ChartPanel(chart)
  setContentPane(panel)

  def createDataset(): XYDataset = {
    val dataset = new XYSeriesCollection()
    //Boys (Age,weight) series
    val series1 = new XYSeries("Boys")
    series1.add(1, 72.9)
    series1.add(2, 81.6)
    series1.add(3, 88.9)
    series1.add(4, 96)
    series1.add(5, 102.1)
    series1.add(6, 108.5)
    series1.add(7, 113.9)
    series1.add(8, 119.3)
    series1.add(9, 123.8)

    dataset.addSeries(series1)

    //Girls (Age,weight) series
    val series2 = new XYSeries("Girls")
    series2.add(1, 72.5)
    series2.add(2, 80.1)
    series2.add(3, 87.2)
    series2.add(4, 94.5)
    series2.add(5, 101.4)
    series2.add(6, 107.4)
    series2.add(7, 112.8)
    series2.add(8, 118.2)
    series2.add(9, 122.9)
    series2.add(10, 123.4)

    dataset.addSeries(series2)

    dataset
  }
}

object ScatterPlotExampleTest extends App {
  SwingUtilities.invokeLater(() => {
    val example = new ScatterPlotExample("Scatter Chart Example | BORAJI.COM")
    example.setSize(800, 400)
    example.setLocationRelativeTo(null)
    example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    example.setVisible(true)
  })
}
