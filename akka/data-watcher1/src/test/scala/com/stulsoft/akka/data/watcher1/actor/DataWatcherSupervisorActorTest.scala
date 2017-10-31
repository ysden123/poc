/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.data.watcher1.actor

import akka.actor.{ActorSystem, Props}
import akka.testkit.{EventFilter, TestKit}
import com.stulsoft.akka.data.watcher1.StopSystemAfterAll
import com.stulsoft.akka.data.watcher1.actor.DataWatcherSupervisorActor.StartDataWatcher
import com.stulsoft.akka.data.watcher1.actor.DataWatcherSupervisorActorTest._
import com.typesafe.config.ConfigFactory
import org.scalamock.scalatest.MockFactory
import org.scalatest.{MustMatchers, WordSpecLike}

/** Unit tests for DataWatcherSupervisorActor
  *
  * @author Yuriy Stul
  */
class DataWatcherSupervisorActorTest extends TestKit(testSystem)
  with MockFactory
  with WordSpecLike
  with MustMatchers
  with StopSystemAfterAll {
  "DataWatcherSupervisorActor" must {
    "create new instance" in {
      system.actorOf(Props[DataWatcherSupervisorActor])
      expectNoMsg()
    }

    "receive should handle message StartDataWatch" in {
      val actor = system.actorOf(Props[DataWatcherSupervisorActor])
      val msg = StartDataWatcher("source1")
      EventFilter.info(message = "Started data watcher for source1").intercept {
        actor ! msg
      }
      expectNoMsg()
    }
  }

}

object DataWatcherSupervisorActorTest {
  val testSystem: ActorSystem = {
    val config = ConfigFactory.parseString(
      """
         akka.loggers = [akka.testkit.TestEventListener,akka.event.slf4j.Slf4jLogger]
      """)
    ActorSystem("testSystem", config)
  }
}