package com.stulsoft.akka.publish.subscribe

import akka.actor.{Actor, ActorLogging}

/**
  * @author Yuriy Stul.
  */
class Actor1 extends Actor with ActorLogging {
  override def receive: Receive = {
    case x => println(s"(${self.path.name}) Received $x")
  }
}
