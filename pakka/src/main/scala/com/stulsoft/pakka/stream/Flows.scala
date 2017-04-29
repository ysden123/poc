package com.stulsoft.akka.stream

import akka.NotUsed
import akka.stream.OverflowStrategy
import akka.stream.scaladsl.{Flow, Sink, Source}
import com.typesafe.scalalogging.LazyLogging

/**
  * The flow is a processing step within the
  * stream. It combines one incoming channel
  * and one outgoing channel as well as
  * some transformation of the messages
  * passing through it. Akka Streams offers
  * a rich DSL which is helpful to define
  * different types of simple flows
  * encompassing user defined behaviour:
  *
  * See [[https://opencredo.com/introduction-to-akka-streams-getting-started/]]
  * Created by Yuriy Stul on 10/24/2016.
  */
object Flows extends App with LazyLogging {
  logger.info("start")

  // Explicitly creating and wiring up a Source, Sink and Flow
  Source(1 to 6).via(Flow[Int].map(_ * 2)).to(Sink.foreach(println(_)))

  // Starting from a Source
  val source = Source(1 to 6).map(_ * 2)
  source.to(Sink.foreach(println(_)))

  // Starting from a Sink
  val sink: Sink[Int, NotUsed] = Flow[Int].map(_ * 2).to(Sink.foreach(println(_)))
  Source(1 to 6).to(sink)

  // Broadcast to a sink inline
  val otherSink: Sink[Int, NotUsed] =
  Flow[Int].alsoTo(Sink.foreach(println(_))).to(Sink.ignore)
  Source(1 to 6).to(otherSink)

  val flowDoublingElements = Flow[Int].map(_ * 2)
  val flowFilteringOutOddElements = Flow[Int].filter(_ % 2 == 0)
  val flowBatchingElements = Flow[Int].grouped(10)
  val flowBufferingElements = Flow[String].buffer(1000, OverflowStrategy.backpressure) // back-pressures the source if the buffer is full
  logger.info("end")
}
