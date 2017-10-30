/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.data.watcher1.actor

import akka.actor.{ActorSystem, Props}
import akka.testkit.TestKit
import com.stulsoft.akka.data.watcher1.StopSystemAfterAll
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
  }

}

object DataWatcherSupervisorActorTest {
  val testSystem: ActorSystem = {
    val config = ConfigFactory.parseString(
      """
         akka.loggers = [akka.testkit.TestEventListener]
      """)
    ActorSystem("testSystem", config)
  }
}