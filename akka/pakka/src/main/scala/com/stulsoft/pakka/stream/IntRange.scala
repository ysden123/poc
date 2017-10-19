package com.stulsoft.akka.stream

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl._
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by Yuriy Stul on 10/23/2016.
  */
object IntRange extends App with LazyLogging {
  logger.info("Start")

  implicit val system = ActorSystem("QuickStart")
  implicit val materializer = ActorMaterializer()

  // Create simple source, emitting the integers 1 to 100:
  val source: Source[Int, NotUsed] = Source(1 to 100)

  // Get numbers:
  val done = source.runForeach(i => println(i))(materializer)

  Await.result(done, 5.seconds)

  system.terminate()

  logger.info("End")
}
