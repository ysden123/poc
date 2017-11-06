package com.stulsoft.pakka.poison

import akka.actor.{Actor, ActorLogging, ActorSystem, PoisonPill, Props}
import com.typesafe.scalalogging.LazyLogging

import scala.io.StdIn

/** Playing with Poison
  *
  * @author Yuriy Stul.
  */
object PoisonEx1 extends App with LazyLogging {
  test()

  def test(): Unit = {
    logger.info("Started test")
    val system = ActorSystem("testPoison")
    val actor = system.actorOf(Props(new Actor1))
    actor ! "Hello"
    Thread.sleep(100)
    actor ! PoisonPill // Stop Actor
    StdIn.readLine()
    system.terminate()
    logger.info("Finishted test")
  }

  class Actor1 extends Actor with ActorLogging {
    override def receive: Receive = {
      case x => log.info(s"Received $x")
    }

    override def postStop(): Unit = {
      log.info("Stopping Actor1")
      super.postStop()
    }
  }

}
