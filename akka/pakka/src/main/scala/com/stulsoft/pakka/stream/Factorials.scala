package com.stulsoft.pakka.stream

import java.nio.file.Paths

import akka.{Done, NotUsed}
import akka.actor.ActorSystem
import akka.stream.scaladsl.{FileIO, Flow, Keep, Sink, Source}
import akka.stream.{ActorMaterializer, IOResult, ThrottleMode}
import akka.util.ByteString
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

/**
  * Created by Yuriy Stul on 10/24/2016.
  */
object Factorials extends App with LazyLogging {
  logger.info("Start")
  implicit val system = ActorSystem("QuickStart")
  implicit val materializer = ActorMaterializer()

  // Create simple source, emitting the integers 1 to 100:
  val source: Source[Int, NotUsed] = Source(1 to 100)

  // Scan emits the initial value and then every calculation result
  val factorials = source.scan(BigInt(2))((acc, next) => acc * next)

  // Output every calculation
  val result0 = factorials.runForeach(f => println(f))
  Await.result(result0, 5.seconds)

  // Writes to file every calculation result (101 lines: initial value and 100 calculations)
  val result1: Future[IOResult] =
  factorials
    .map(num => ByteString(s"$num\n"))  // convert to string
    .runWith(FileIO.toPath(Paths.get("factorials.txt")))
  Await.result(result1, 5.seconds)

  // Reusable pieces
  def linkSink(filename: String): Sink[String, Future[IOResult]] =
  Flow[String]
    .map(s => ByteString(s + "\n"))
    .toMat(FileIO.toPath(Paths.get(filename)))(Keep.right)

  val result2 = factorials.map(_.toString).runWith(linkSink("factorials2.txt"))
  Await.result(result2, 5.seconds)

  // Time-based processing
  val done: Future[Done] =
  factorials
    .zipWith(Source(0 to 10))((num, idx) => s"$idx! = $num")
    // one element per second. the second 1 in the argument list is the maximum size of a burst that we want
    // to allow - passing 1 means that the first element gets through immediately
    // and the second then has to wait for one second and so on
    .throttle(1, 1.second, 1, ThrottleMode.shaping)
    .runForeach(println)
  Await.result(done, 11.seconds)

  system.terminate()
  logger.info("End")
}
