package com.stulsoft.scala.gradle

/**
  * @author Yuriy Stul.
  */
class ScalaClass1(val name: String, val age: Int) {
  def nameWithAge(): String = s"$name-$age"
}
