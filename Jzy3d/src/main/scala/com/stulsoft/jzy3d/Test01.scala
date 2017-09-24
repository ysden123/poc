/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.jzy3d

import org.jzy3d.chart.AWTChart
import org.jzy3d.colors.colormaps.ColorMapRainbow
import org.jzy3d.colors.{Color, ColorMapper}
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid
import org.jzy3d.plot3d.builder.{Builder, Mapper}
import org.jzy3d.plot3d.primitives.Shape
import org.jzy3d.plot3d.rendering.canvas.Quality


/**
  * @see [[http://jzy3d.org/]]
  * @author Yuriy Stul
  */
object Test01 extends App {
  test()

  def test(): Unit = {
    val mapper: Mapper = (x: Double, y: Double) => 10 * Math.sin(x / 10) * Math.cos(y / 20)

    // Define range and precision for the function to plot// Define range and precision for the function to plot
    val range: org.jzy3d.maths.Range = new org.jzy3d.maths.Range(-150, 150)
    val steps: Int = 50

    // Create a surface drawing that function
    val surface: Shape = Builder.buildOrthonormal(new OrthonormalGrid(range, steps), mapper)
    surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds.getZmin, surface.getBounds.getZmax, new Color(1, 1, 1, .5f)))
    surface.setFaceDisplayed(true)
//    surface.setWireframeDisplayed(false)
    surface.setWireframeDisplayed(true)
    surface.setWireframeColor(Color.BLACK)

    // Create a chart and add the surface// Create a chart and add the surface
    val chart = new AWTChart(Quality.Advanced)
    chart.add(surface)
    chart.open("Jzy3d Demo", 600, 600)
  }

}
