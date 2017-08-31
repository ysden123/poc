/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.mailOffice

import akka.actor.{Actor, ActorLogging, Props}

import scala.util.Random

/**
  * Handles a mail
  *
  * @author Yuriy Stul
  */
class MailHandler extends Actor with ActorLogging {
  override def receive: Receive = {
    case Mail(from, to, subject, body) =>
      log.info("""Started   handling mail from "{}", to "{}", with subject "{}": {} """, from, to, subject, body)
      Thread.sleep(Random.nextInt(1000))
      log.info("""Completed handling mail from "{}", to "{}", with subject "{}": {} """, from, to, subject, body)

      // Self-stop
      context.system.stop(self)
  }
}

object MailHandler {
  def props: Props = Props[MailHandler]
}