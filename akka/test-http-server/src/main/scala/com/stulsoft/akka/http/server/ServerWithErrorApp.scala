/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.akka.http.server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.LazyLogging

/** Simple HTTP server with error as answer
  *
  * <p>Receives any request, outputs headers and body, returns error.
  *
  * @author Yuriy Stul
  */
object ServerWithErrorApp extends App with LazyLogging {
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  logger.info("==>ServerApp")
  val route = extractRequest { request => {
    logger.info(s"uri: ${request.uri}")
    logger.info(s"method: ${request.method.name()}")
    logger.info("headers:")
    request.headers.foreach(header => logger.info(s"${header.name()}: ${header.value()}"))

    entity(as[String]) { body => {
      logger.info("body: {}", body)
      respondWithHeaders(RawHeader("TestHeader", "test value")) {
        complete(BadRequest, "test text")
      }
    }
    }
  }
  }

  Http().bindAndHandle(route, "localhost", 8080)
  println("Started. Try http://localhost:8080")
  logger.info("Started")
}
