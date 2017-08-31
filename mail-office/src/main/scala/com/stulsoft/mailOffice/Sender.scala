/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.mailOffice

import akka.actor.{Actor, ActorLogging, Props}

/** Sends mails to receiver actor
  *
  * @author Yuriy Stul
  */
class Sender extends Actor with ActorLogging {
  override def receive: Receive = {
    case command: String if command == "start" =>
      for (i <- 1 to 10) {
        context.actorSelection("/user/" + ReceiverActorName) ! Mail("from me", "to me", "test", s"It is simple test # $i")
      }
  }
}

object Sender {
  def props: Props = Props[Sender]
}