package com.stulsoft.pakka.http.client.simple

import akka.actor.ActorSystem
import com.typesafe.scalalogging.LazyLogging
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Simple HTTP client.
  *
  * Gets and outputs a HTML page.
  *
  * Created by Yuriy Stul on 10/23/2016.
  */
object SimpleClient1 extends App with LazyLogging {
  logger.info("Started")
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  val responseFuture: Future[HttpResponse] =
    Http().singleRequest(HttpRequest(uri = "http://akka.io"))

  val result = Await.result(responseFuture, 5.seconds)

  logger.debug("Status is {}", result.status)
  logger.debug("Content type is {}", result.entity.contentType)

  val resultAsString = Await.result(Unmarshal(result.entity).to[String], 5.seconds)
  println(resultAsString)

  logger.info("Finished")

  materializer.shutdown()
  sys.exit(0)
}
