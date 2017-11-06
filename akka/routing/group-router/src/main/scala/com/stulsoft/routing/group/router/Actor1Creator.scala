package com.stulsoft.routing.group.router

import akka.actor.{Actor, ActorLogging, Props}

/**
  * @author Yuriy Stul.
  */
class Actor1Creator(nrActors: Int) extends Actor with ActorLogging {

  override def preStart(): Unit = {
    super.preStart()
    log.info("Creating Actor1 actors")
    (1 to nrActors).map { nr =>
      context.actorOf(Props[Actor1], "Actor1" + nr)
    }
  }

  override def receive: Receive = {
    case _ =>
  }
}
