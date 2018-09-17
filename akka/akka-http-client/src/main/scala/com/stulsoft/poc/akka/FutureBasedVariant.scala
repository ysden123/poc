/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.akka

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

/** Future Based Variant of Akka HTTP Client.
  * <p>
  * See [[https://doc.akka.io/docs/akka-http/current/client-side/request-level.html#future-based-variant Future-Based Variant]]
  *
  * @author Yuriy Stul
  */
object FutureBasedVariant extends App with LazyLogging {
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = "https://www.smugmug.com/"))

  def exit(code: Int): Unit = {
    materializer.shutdown()
    sys.exit(code)
  }

  responseFuture
    .onComplete {
      case Success(res) =>
        logger.info(res.toString())
        logger.info(s"status = ${res.status}")
        logger.info(s"Entity: ${Unmarshal(res.entity).to[String]}")

        res.discardEntityBytes()
        exit(0)
      case Failure(x) =>
        logger.error("something wrong", x)
        materializer.shutdown()
        exit(1)
    }
}
