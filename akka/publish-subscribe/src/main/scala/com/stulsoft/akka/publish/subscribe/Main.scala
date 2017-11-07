package com.stulsoft.akka.publish.subscribe

import akka.actor.{ActorSystem, Props}
import com.typesafe.scalalogging.LazyLogging

import scala.io.StdIn

/** Playing with publishing-subscribing
  *
  * @author Yuriy Stul.
  */
object Main extends App with LazyLogging {
  start()

  def start(): Unit = {
    logger.info("Started")
    val system = ActorSystem("publishSubscribeSystem")
    val a1 = system.actorOf(Props[Actor1], "actor1")
    val a2 = system.actorOf(Props[Actor1], "actor2")

    // Subscribe Actor1 actors to receive String message
    system.eventStream.subscribe(
      a1,
      classOf[String]
    )
    system.eventStream.subscribe(
      a2,
      classOf[String]
    )

    // Publish message 1
    system.eventStream.publish("test message 1")

    // Unsubscribe a2
    system.eventStream.unsubscribe(a2)

    // Publish message 2
    system.eventStream.publish("test message 2")

    StdIn.readLine()
    system.terminate()
    logger.info("Finished")
  }
}
