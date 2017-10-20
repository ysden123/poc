/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.tdd.sideeffectingactor

import akka.actor.{ActorSystem, Props}
import akka.testkit.{CallingThreadDispatcher, EventFilter, TestKit}
import com.stulsoft.akka.tdd.StopSystemAfterAll
import com.stulsoft.akka.tdd.sideeffectingactor.Greeter01Test._
import com.typesafe.config.ConfigFactory
import org.scalatest.WordSpecLike

/**
  * The Greeter01 is tested by inspecting the log messages that it writes using the Actor-
  * Logging trait. The test kit module provides a TestEventListener that you can configure
  * to handle all events that are logged. The ConfigFactory can parse a configuration
  * file from a String; in this case we only override the event handlers list.
  */
class Greeter01Test extends TestKit(testSystem) // Uses the testSystem from the Greeter01Test object
  with WordSpecLike
  with StopSystemAfterAll {
  "The Greeter01" must {
    """say Hello World! when a Greeting("World") is sent to it""" in {
      val dispatcherId = CallingThreadDispatcher.Id

      // Single-threaded environment
      val props = Props[Greeter01].withDispatcher(dispatcherId)

      val greeter = system.actorOf(props)

      //Intercepts the log messages that were logged
      EventFilter.info(message = "Hello World!", occurrences = 1).intercept {
        greeter ! Greeting01("World")
      }
    }
  }
}

object Greeter01Test {
  // Creates a system with a configuration that attaches a test event listener
  val testSystem: ActorSystem = {
    val config = ConfigFactory.parseString(
      """
         akka.loggers = [akka.testkit.TestEventListener]
      """)
    ActorSystem("testsystem", config)
  }
}