/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.stream
import akka.actor.{Actor, ActorRef, Props}
import com.stulsoft.akka.stream.Producer.{EndOfFileStream, Line, Produce}

import scala.io.Source

/**
  * @author Yuriy Stul
  */
object Producer {
  case object Produce
  case class Line(text: String, producer: Option[ActorRef])
  case object EndOfFileStream

  def props(processingNode: ActorRef) = Props(new Producer(processingNode))
}

class Producer(processingNode: ActorRef) extends Actor {

  private val lineStream = Source.fromFile(Utils.getResourceFilePath("cano.txt")).getLines

  def receive:Actor.Receive = {
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