/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.tdd.sideeffectingactor

import akka.actor.{ActorSystem, UnhandledMessage}
import akka.testkit.TestKit
import com.stulsoft.akka.tdd.StopSystemAfterAll
import org.scalatest.WordSpecLike

/**
  * The Greeter01 is tested by inspecting the log messages that it writes using the Actor-
  * Logging trait. The test kit module provides a TestEventListener that you can configure
  * to handle all events that are logged. The ConfigFactory can parse a configuration
  * file from a String; in this case we only override the event handlers list.
  */
class Greeter02Test extends TestKit(ActorSystem("testsystem"))
  with WordSpecLike
  with StopSystemAfterAll {
  "The Greeter" must {
    """say Hello World! when a Greeting("World") is sent to it""" in {
      val props = Greeter02.props(Some(testActor)) // Sets the listener to the testActor
      val greeter = system.actorOf(props, "greeter02-1")
      greeter ! Greeting02("World")
      expectMsg("Hello World!") // Asserts the message as usual
    }
    "say something else and see what happens" in {
      val props = Greeter02.props(Some(testActor))
      val greeter = system.actorOf(props, "greeter02-2")
      system.eventStream.subscribe(testActor, classOf[UnhandledMessage])
      greeter ! "World"
      expectMsg(UnhandledMessage("World", system.deadLetters, greeter))
    }
  }
}