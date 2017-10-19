/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.tdd

import akka.actor.{Actor, ActorRef}

/**
  * @see Akka in Action by Raymond Roestenburg, Rob Bakker and Rob Williams
  * @author Yuriy Stul
  */
class SilentActor extends Actor {

  import SilentActor._

  private var internalState = Vector[String]()

  def receive: Receive = {
    case SilentMessage(data) =>
      internalState = internalState :+ data
    case GetState(receiver) =>
      receiver ! internalState
  }

  def state: Vector[String] = internalState
}

object SilentActor {

  case class SilentMessage(data: String)

  case class GetState(receiver: ActorRef)

}