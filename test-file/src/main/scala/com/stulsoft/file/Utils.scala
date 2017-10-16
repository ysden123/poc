package com.stulsoft.file

import java.io.File
import java.net.URI

import scala.io.Source

/**
  * @author Yuriy Stul.
  */
object Utils {
  def source(name:String):Source={
    Source.fromResource(name, getClass.getClassLoader)
  }
}
