package com.stulsoft.akka.stream

import akka.stream.scaladsl.Sink
import com.typesafe.scalalogging.LazyLogging

/**
  * The sink is the ultimate destination of all
  * the messages flowing through the stream.
  * The library supports a number of
  * out-of-the-box sink implementations:
  *
  * See [[https://opencredo.com/introduction-to-akka-streams-getting-started/]],
  * [[http://doc.akka.io/docs/akka/2.4/scala/stream/stream-flows-and-basics.html]]
  * Created by Yuriy Stul on 10/24/2016.
  */
object Sinks extends App with LazyLogging {
  logger.info("start")
  val sinkPrintingOutElements = Sink.foreach[String](println(_))

  // Sink that folds over the stream and returns a Future
  // of the final result as its materialized value
  val sinkCalculatingASumOfElements = Sink.fold[Int, Int](0)(_ + _)

  // Sink that returns a Future as its materialized value,
  // containing the first element of the stream
  val sinkReturningTheFirstElement = Sink.head

  // A Sink that consumes a stream without doing anything with the elements
  val sinkNoop = Sink.ignore

  // A Sink that executes a side-effecting call for every element of the stream
  Sink.foreach[String](println(_))

  logger.info("end")
}
