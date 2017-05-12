package com.stulsoft.pkafka.scala

import java.net.{ConnectException, HttpURLConnection, SocketException, URL}

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
  * Check connection.
  *
  * @author Yuriy Stul
  */
object CheckConnection {
  val logger: Logger = LoggerFactory.getLogger(CheckConnection.getClass)

  def checkConnection(): Boolean = {
    var available: Boolean = false
    val urlString: String = "http://localhost:9092"

    try {
      val url: URL = new URL(urlString)
      val con: HttpURLConnection = url.openConnection.asInstanceOf[HttpURLConnection]
      con.setRequestMethod("GET")
      val responseCode: Int = con.getResponseCode
      logger.debug("responseCode={}", responseCode)
    }
    catch {
      case e: ConnectException => logger.error("No connection with Kafka server. Error: {}", e.getMessage)
      case e: SocketException =>
        logger.info("Kafka server is available. {}", e.getMessage)
        available = true
      case e: Exception => logger.error("Failure", e)
    }
    available
  }

  def main(args: Array[String]): Unit = {
    logger.debug("==>CheckConnection")
    logger.debug("Is Kafka server available: {}", checkConnection())
    logger.debug("<==CheckConnection")
  }
}
