/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.akka.http.server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.stulsoft.scala.tools.xml.XMLValidator
import com.typesafe.scalalogging.LazyLogging

import scala.io.Source
import scala.util.{Failure, Success}

/**
  * @author Yuriy Stul
  */
case class Server(config: Configuration) extends LazyLogging {
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  val headers: scala.collection.immutable.List[RawHeader] = config
    .headers
    .map(header => RawHeader(header.name, header.value)).toList
  private val xmlSource = Source.fromResource(config.xmlFile)
  private val xml = xmlSource.mkString
  xmlSource.close()

  private val route = extractRequest {
    request => {
      logger.info(s"uri: ${request.uri}")
      logger.info(s"method: ${request.method.name()}")
      logger.info(s"request.headers.length = ${request.headers.length}")
      logger.info("headers:")
      request.headers.foreach(header => logger.info(s"${header.name()}: ${header.value()}"))

      entity(as[String]) {
        body => {
          logger.info("body: {}", body)
          if (config.validateXmlRequest) {
            XMLValidator.validate(body) match {
              case Success(_) => logger.info("XML request is valid")
              case Failure(ex) => logger.error("XML request is NOT valid: {}", ex.getMessage)
            }
          }
          config.headers.map(header => RawHeader(header.name, header.value))
          respondWithHeaders(headers) {
            val response = HttpEntity(ContentTypes.`text/xml(UTF-8)`, xml)
            complete(config.statusCode, response)
          }
        }
      }

    }
  }

  Http().bindAndHandle(route, "localhost", 8080)
  logger.info("Started on localhost:8080")
}
