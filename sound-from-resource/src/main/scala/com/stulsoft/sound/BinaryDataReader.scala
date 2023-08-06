/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.sound

import com.typesafe.scalalogging.StrictLogging

import java.io
import java.io.{ByteArrayInputStream, InputStream}
import scala.util.Try


object BinaryDataReader extends StrictLogging:
  def readBinaryDataFromResource(resourceName: String): Try[ByteArrayInputStream] =
    Try {
      var inputStream: InputStream = null
      try {
        inputStream = getClass.getClassLoader.getResourceAsStream(resourceName)
        val buffer = Array.ofDim[Byte](1024 * 10) // You can adjust the buffer size as needed
        var bytesRead = 0
        val byteArrayStream = new java.io.ByteArrayOutputStream()

        while ( {
          bytesRead = inputStream.read(buffer)
          bytesRead
        } != -1) {
          byteArrayStream.write(buffer, 0, bytesRead)
        }
        new ByteArrayInputStream(byteArrayStream.toByteArray)
      }
      catch
        case exception: Exception =>
          logger.error(exception.getMessage, exception)
          throw exception
      finally {
        if (inputStream != null) {
          inputStream.close()
        }
      }
    }