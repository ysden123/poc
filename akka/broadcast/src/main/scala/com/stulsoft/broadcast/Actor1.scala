package com.stulsoft.broadcast

import akka.actor.{Actor, ActorLogging}

/**
  * @author Yuriy Stul.
  */
class Actor1 extends Actor with ActorLogging {
  override def preStart(): Unit = {
    super.preStart()
    log.info("Starting Actor1")
  }

  override def receive: Receive = {
    case x => log.info(s"Actor1: received $x")
  }
}
