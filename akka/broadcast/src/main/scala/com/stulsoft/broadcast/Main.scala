package com.stulsoft.broadcast

import akka.actor.{ActorSystem, Props}
import com.typesafe.scalalogging.LazyLogging


/**
  * @author Yuriy Stul.
  */
object Main extends App with LazyLogging {
  logger.info("Started")
  val system = ActorSystem("question-app")
  val a1 = system.actorOf(Props[Actor1])
  val a2 = system.actorOf(Props[Actor2])

  var actors = system.actorSelection("/user/*")
  logger.info(s"(1) actors: $actors")
  actors ! "test message 1"

  Thread.sleep(1000)
  system.terminate()
  logger.info("Stopped")
}
