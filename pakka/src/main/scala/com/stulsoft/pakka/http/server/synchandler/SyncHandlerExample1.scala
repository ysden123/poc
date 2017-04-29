package com.stulsoft.pakka.http.server.synchandler

import java.nio.file.Paths

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Usage low-level server side API
  *
  * Created by Yuriy Stul on 10/21/2016.
  */
object SyncHandlerExample1 extends App with LazyLogging {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  val serverSource = Http().bind(interface = "localhost", port = 8080)
  val requestHandler: HttpRequest => HttpResponse = {
    case HttpRequest(GET, Uri.Path("/"), _, _, _) =>
      val resourcesPath = getClass.getResource("/index1.html")
      HttpResponse(entity = HttpEntity.fromPath(ContentTypes.`text/html(UTF-8)`, Paths.get(resourcesPath.toURI)))

    case HttpRequest(GET, Uri.Path("/ping"), _, _, _) =>
      HttpResponse(entity = "PONG!")

    case HttpRequest(GET, Uri.Path("/crash"), _, _, _) =>
      logger.error("BOOM!")
      sys.error("BOOM!")

    case HttpRequest(GET, Uri.Path("/stop"), _, _, _) =>
      Future {
        materializer.shutdown()
        logger.info("Stopped")
        sys.exit(0)
      }
      HttpResponse(entity = "Stopped")

    case HttpRequest(GET, Uri.Path("/favicon.ico"), _, _, _) =>
      HttpResponse(entity = "")

    case r: HttpRequest =>
      logger.warn(s"Unknown resource! ${r.uri}")
      HttpResponse(404, entity = "Unknown resource!")
  }

  val bindingFuture: Future[Http.ServerBinding] =
    serverSource.to(Sink.foreach { connection =>
      logger.info("Accepted new connection from " + connection.remoteAddress)
      connection handleWithSyncHandler requestHandler
      // this is equivalent to
      // connection handleWith { Flow[HttpRequest] map requestHandler }
    }).run()
  println("Started. Try http://localhost:8080")
  logger.info("Started")
}
