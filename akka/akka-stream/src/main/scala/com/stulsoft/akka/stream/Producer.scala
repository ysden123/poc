/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.stream

import akka.actor.{Actor, ActorRef, Props}
import com.stulsoft.akka.stream.Messages.{EndOfFileStream, Line, Produce}

import scala.util.{Failure, Success}

/**
  * @author Yuriy Stul
  */
object Producer {

  def props(processingNode: ActorRef) = Props(new Producer(processingNode))

}

class Producer(processingNode: ActorRef) extends Actor {

  var lineStream: Iterator[String] = _
  Utils.source("cano.txt") match {
    case Success(source) => lineStream = source.getLines()
    case Failure(e) =>
      println(e.getMessage)
      System.exit(1)
  }

  def receive: Actor.Receive = {
    case Produce =>
      val iterator = lineStream
      if (iterator.hasNext) {
        val text = iterator.next()
        processingNode ! Line(text.trim, Some(self))
      } else {
        processingNode ! EndOfFileStream
      }
  }
}