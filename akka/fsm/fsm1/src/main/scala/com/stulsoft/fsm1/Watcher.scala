package com.stulsoft.fsm1

import akka.actor.{Actor, ActorLogging}

/**
  * @author Yuriy Stul.
  */
class Watcher extends Actor with ActorLogging{
  override def receive: Receive = {
    case x => log.info("Watcher received {}", x)
  }
}
