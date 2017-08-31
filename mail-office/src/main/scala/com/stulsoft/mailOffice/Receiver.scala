/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.mailOffice

import akka.actor.{Actor, ActorLogging, Props}

/** Receives mails and forwards then to mail handlers
  *
  * @author Yuriy Stul
  */
class Receiver extends Actor with ActorLogging {
  override def receive: Receive = {
    case Mail(from, to, subject, body) =>
      log.info("""Received mail from "{}", to "{}", with subject "{}": {} """, from, to, subject, body)

      // Create new mail handler actor and forward to it a mail
      context.system.actorOf(MailHandler.props) ! Mail(from, to, subject, body)
  }
}

object Receiver {
  def props: Props = Props[Receiver]
}