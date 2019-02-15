package com.stulsoft.akka.http.server.synchandler

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Usage high-level server side API
  *
  * Created by Yuriy Stul on 10/21/2016.
  */
object SyncHandlerExample2 extends App with LazyLogging {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  val route = get {
    pathSingleSlash {
      getFromResource("index2.html", ContentTypes.`text/html(UTF-8)`)
    } ~
      path("ping") {
        complete("PONG!")
      } ~
      path("crash") {
        logger.error("BOOM!")
        sys.error("BOOM!")
      } ~
      path("favicon.ico") {
        complete("")
      } ~
      path("stop") {
        Future {
          Thread.sleep(500)
          materializer.shutdown()
          logger.info("Stopped")
          sys.exit(0)
        }
        complete("Stopped")
      }
  }

  Http().bindAndHandle(route, "localhost", 8080)
  println("Started. Try http://localhost:8080")
  logger.info("Started")
}
