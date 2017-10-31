package com.stulsoft.broadcast

import akka.actor.{Actor, ActorLogging, Props}

/**
  * @author Yuriy Stul.
  */
class Actor2 extends Actor with ActorLogging {
  override def preStart(): Unit = {
    super.preStart()
    log.info("Starting Actor2")
    context.actorOf(Props[Actor3])
  }

  override def receive: Receive = {
    case x =>
      log.info(s"Actor2: received $x")
      context.actorSelection("*") ! x // forward message
  }
}
