/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.akka.stream.first.step

import akka.stream._
import akka.stream.scaladsl._
import akka.{Done, NotUsed}
import akka.actor.ActorSystem

import scala.concurrent._
import scala.util.{Failure, Success}

/**
  * @author Yuriy Stul
  */
object FirstStep extends App {
  implicit val system: ActorSystem = ActorSystem("FirstStep")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContextExecutor = system.dispatcher

  val source: Source[Int, NotUsed] = Source(1 to 10)

  val factorials = source.scan(BigInt(1))((acc, next) => acc * next)
  factorials.runForeach(r => println(s"r=$r"))
  val result = factorials.runReduce((_, second) => second)
  result.onComplete {
    {
      case Success(value) => println(s"result is $value")
      case Failure(exception) => println(s"Error: ${exception.getMessage}")
    }
  }

  val done: Future[Done] = source.runForeach(i ⇒ println(i))

  done.onComplete(_ ⇒ system.terminate())
}
