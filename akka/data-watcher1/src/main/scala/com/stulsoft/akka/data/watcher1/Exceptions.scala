/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.data.watcher1

/**
  * @author Yuriy Stul
  */
object Exceptions {

  case class DiskErrorException(message: String, cause: Throwable = None.orNull) extends RuntimeException(message, cause)

  case class FileCorruptException(message: String, cause: Throwable = None.orNull) extends RuntimeException(message, cause)

  case class DBConnectionException(message: String, cause: Throwable = None.orNull) extends RuntimeException(message, cause)

}