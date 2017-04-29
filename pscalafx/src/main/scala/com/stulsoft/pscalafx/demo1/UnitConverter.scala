package com.stulsoft.pscalafx.demo1

/**
  * @see [[http://vigoo.github.io/posts/2014-01-12-scalafx-with-fxml.html ScalaFX with FXML]]
  * @see [[https://github.com/vigoo/scalafxml/blob/master/demo/src/main/scala/scalafxml/demo/unitconverter/UnitConverter.scala original]]
  * @author Yuriy Stul
  */
trait UnitConverter {
  val description: String

  def run(input: String): String

  override def toString: String = description
}

class UnitConverters(converters: UnitConverter*) {
  val available = List(converters: _*)
}

object MMtoInches extends UnitConverter {
  val description: String = "Millimeters to inches"

  def run(input: String): String = try {
    if (input.isEmpty) "" else (input.toDouble / 25.4).toString
  } catch {
    case ex: Throwable => ex.toString
  }
}

object InchesToMM extends UnitConverter {
  val description: String = "Inches to millimeters"

  def run(input: String): String = try {
    if (input.isEmpty) "" else  (input.toDouble * 25.4).toString
  } catch {
    case ex: Throwable => ex.toString
  }
}