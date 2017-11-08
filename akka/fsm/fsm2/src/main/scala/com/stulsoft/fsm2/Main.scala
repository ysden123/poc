package com.stulsoft.fsm2

import akka.actor.FSM.SubscribeTransitionCallBack
import akka.actor.{ActorSystem, Props}
import com.typesafe.scalalogging.LazyLogging

import scala.io.StdIn

/**
  * @author Yuriy Stul.
  */
object Main extends App with LazyLogging {
  start()

  def start(): Unit = {
    logger.info("Started")
    val system = ActorSystem("inventorySystem")
    // Create publisher actor
    val publisher = system.actorOf(Props(new Publisher(2, 2)))

    // Create watcher actor
    val watcher = system.actorOf(Props(new Watcher()))

    // Create inventory actor
    val inventory = system.actorOf(Props(new Inventory(publisher)))

    // Watcher is subscribed to transition notifications
    inventory ! SubscribeTransitionCallBack(watcher)

    inventory ! BookRequest("context1", publisher)

/*
    println("Enter line to exit")
    StdIn.readLine()
*/
    Thread.sleep(6500)

    system.terminate()

    logger.info("Finished")
  }
}
