package com.stulsoft.file

import scala.io.Source
import scala.util.{Failure, Success, Try}

/**
  * @author Yuriy Stul.
  */
object Utils {
  def source(name: String): Try[Source] = {
    try {
      Success(Source.fromResource(name, getClass.getClassLoader))
    }
    catch {
      case e: Exception => Failure(e)
    }
  }
}
