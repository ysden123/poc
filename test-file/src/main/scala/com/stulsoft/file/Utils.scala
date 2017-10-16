package com.stulsoft.file

import java.io.FileNotFoundException

import scala.io.Source
import scala.util.{Failure, Success, Try}

/**
  * @author Yuriy Stul.
  */
object Utils {
  def source(name: String): Try[Source] = {
    try {
      val s = Source.fromResource(name)
      Success(s)
//      Try(Source.fromResource(name)).recover(throw new FileNotFoundException(name))
    }
    catch {
      case e: Exception =>
        try {
          Try(Source.fromFile(name))
        }
        catch {
          case e: Exception => Failure(e)
        }
    }
  }
}
