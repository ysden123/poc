package com.stulsoft.broadcast

import akka.actor.{Actor, ActorLogging}

/**
  * @author Yuriy Stul.
  */
trait ForwardActor extends Actor with ActorLogging {
  def forward(msg: Any): Unit = {
    log.info(s"ForwardActor: forward $msg")
    context.actorSelection("*") ! msg // forward message
  }
}
