/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.stream

import akka.actor.{Actor, ActorRef, Props}
import com.stulsoft.akka.stream.Consumer.Load
import com.stulsoft.akka.stream.ProcessingNode.{StartProducingQuestions, StopProducingQuestions}
import com.stulsoft.akka.stream.Producer.EndOfFileStream

object Consumer {
  def props(uiActor: ActorRef) = Props(new Consumer(uiActor))
  case object Load
}

class Consumer(uiActor: ActorRef) extends Actor {

  private val processingNode = context.parent

  var questions = List.empty[String]
  val maxBoundedQuestions = 10

  def receive:Actor.Receive = {
    case Load =>
      println("Loading questions")
      processingNode ! StartProducingQuestions(None)
    case s: String => questions = s::questions
      if (questions.length < maxBoundedQuestions) {

      } else {
        uiActor ! questions
        questions = List.empty[String]
        processingNode ! StopProducingQuestions
        Thread.sleep(2000)
        println("Loading new batch")
        processingNode ! StartProducingQuestions(None)
      }
    case EndOfFileStream =>
      println("FINISH: No data anymore")
      processingNode ! StopProducingQuestions
  }

}