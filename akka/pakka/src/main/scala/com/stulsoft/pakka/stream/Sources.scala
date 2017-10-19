package com.stulsoft.akka.stream

import akka.stream.scaladsl.Source
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Future


/**
  * The source is the starting point of the stream,
  * this is where the data flowing through the stream
  * originates from. A source can be anything that
  * can generate messages, like a collection,
  * database query or HTTP request.
  * Akka Streams allows creating sources from a variety of data producing entities.
  *
  * See [[https://opencredo.com/introduction-to-akka-streams-getting-started/]]
  *
  * Created by Yuriy Stul on 10/24/2016.
  */
object Sources extends App with LazyLogging {
  logger.info("start")
  val sourceFromRange = Source(1 to 10)
  val sourceFromIterable = Source(List(1, 2, 3))
  val sourceFromFuture = Source.fromFuture(Future.successful("hello"))
  val sourceWithSingleElement = Source.single("just one")
  val sourceEmittingTheSameElement = Source.repeat("again and again")
  val emptySource = Source.empty
  logger.info("end")
}
