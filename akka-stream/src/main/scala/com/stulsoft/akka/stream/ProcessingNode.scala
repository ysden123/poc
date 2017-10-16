/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.stream

import akka.actor.{Actor, ActorRef, Props}
import akka.pattern.pipe
import com.stulsoft.akka.stream.Messages._
import com.stulsoft.akka.stream.ProcessingNode._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object ProcessingNode {

  def props(uiActor: ActorRef) = Props(new ProcessingNode(uiActor))

  def isQuestion(trimmed: String): Boolean = trimmed.endsWith("?") || trimmed.endsWith("?\"")
}

class ProcessingNode(uiActor: ActorRef) extends Actor {

  private val consumer = context.actorOf(Consumer.props(uiActor))
  private val producer = context.actorOf(Producer.props(self))
  var shouldProduce = false

  def receive: Actor.Receive = {
    case StartProducingQuestions(testProducer) =>
      shouldProduce = true
      testProducer.getOrElse(producer) ! Produce
      producer ! Produce
    case StopProducingQuestions =>
      shouldProduce = false
    case Line(text, testProducer) =>
      val trimmed = text.trim
      if (isQuestion(trimmed)) {
        consumer ! trimmed
      }
      if (shouldProduce) {
        testProducer.getOrElse(producer) ! Produce
      }

    case GetConsumer() =>
      Future.successful(consumer) pipeTo sender()

    case ShouldProduce(out) => out ! shouldProduce
    case EndOfFileStream =>
      consumer ! EndOfFileStream
  }

}