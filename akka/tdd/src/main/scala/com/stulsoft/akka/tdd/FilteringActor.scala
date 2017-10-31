/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.tdd

import akka.actor.{Actor, ActorRef, Props}

object FilteringActor {
  def props(nextActor: ActorRef, bufferSize: Int) =
    Props(new FilteringActor(nextActor, bufferSize))

  case class Event(id: Long)

}

class FilteringActor(nextActor: ActorRef,
                     bufferSize: Int) extends Actor {

  import FilteringActor._

  private var lastMessages = Vector[Event]()

  def receive: Receive = {
    case msg: Event =>
      if (!lastMessages.contains(msg)) {
        lastMessages = lastMessages :+ msg
        nextActor ! msg
        if (lastMessages.size > bufferSize) {
          // discard the oldest
          lastMessages = lastMessages.tail
        }
      }
  }
}