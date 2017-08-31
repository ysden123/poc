/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.mailOffice

import akka.actor.ActorSystem


/** Mail office
  *
  * Sender actor sends mails to receiver actor, and receiver actor forwards mails to mal handlers
  *
  * @author Yuriy Stul
  */
object Office extends App {
  val system: ActorSystem = ActorSystem("mailOffice")

  try {

    // Create receiver actor
    system.actorOf(Receiver.props, ReceiverActorName)

    // Create sender actor
    val sender = system.actorOf(Sender.props, SenderActorName)

    // Start sending mails
    sender ! "start"

    // Wait 5 seconds
    Thread.sleep(1000 * 5)
  }
  finally {
    system.terminate()
  }
}
