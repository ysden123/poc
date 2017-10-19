package com.stulsoft.akka.stream

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Keep, RunnableGraph, Sink, Source}
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

/**
  * Created by Yuriy Stul on 10/24/2016.
  */
object Sum1 extends App with LazyLogging {
  logger.info("Start")
  implicit val system = ActorSystem("QuickStart")
  implicit val materializer = ActorMaterializer()

  val source = Source(1 to 10)
  val sink = Sink.fold[Int, Int](0)(_ + _)

  // connect the Source to the Sink, obtaining a RunnableGraph
  val runnable: RunnableGraph[Future[Int]] = source.toMat(sink)(Keep.right)

  // materialize the flow and get the value of the FoldSink
  // Option 1
  var sum: Future[Int] = runnable.run()
  var result = Await.result(sum, 15.seconds)
  logger.debug("result is {}", result)

  // Option 2
  sum = source.runWith(sink)
  result = Await.result(sum, 15.seconds)
  logger.debug("result is {}", result)

  // Note! Immutable
  val source2 = Source(1 to 10)
  source2.map(_ => 0) // has no effect on source, since it's immutable
  sum = source2.runWith(Sink.fold(0)(_ + _))
  result = Await.result(sum, 15.seconds)
  logger.debug("result is {}", result)  // 55

  val zeroes = source2.map(_ => 0)
  sum = zeroes.runWith(Sink.fold(0)(_ + _))
  result = Await.result(sum, 15.seconds)
  logger.debug("result is {}", result)  // 0

  system.terminate()
  logger.info("End")
}
