/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.stream

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import com.stulsoft.akka.stream.Messages.{GetConsumer, Load}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt

object ConsoleQuestionApp extends App {
  val system = ActorSystem("question-app")
  val ui = system.actorOf(Props[ConsoleClient])
  implicit val timeout: Timeout = Timeout(100 second)
  val processingNode = system.actorOf(ProcessingNode.props(ui))
  val consumerFuture = processingNode.ask(GetConsumer()).mapTo[ActorRef]

  consumerFuture.map(ref => ref ! Load)

}