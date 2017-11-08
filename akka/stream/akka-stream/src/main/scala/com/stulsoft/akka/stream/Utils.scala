/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.stream

import scala.io.Source
import scala.util.{Failure, Try}

/**
  * @author Yuriy Stul.
  */
object Utils {
  /**
    * Returns a Source from specified file name.
    *
    * @param name specifies the file; it may be a file in resources and in JAR as well, or it may be any file
    * @return the Source from specified file name.
    */
  def source(name: String): Try[Source] = {
    try {
      if (getClass.getClassLoader.getResourceAsStream(name) != null) {
        Try(Source.fromResource(name))
      } else {
        Try(Source.fromFile(name))
      }
    }
    catch {
      case e: Exception => Failure(e)
    }
  }
}
