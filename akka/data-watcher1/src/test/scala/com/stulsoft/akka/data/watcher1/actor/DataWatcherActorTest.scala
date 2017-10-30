/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.data.watcher1.actor

import akka.actor.ActorSystem
import akka.testkit.{CallingThreadDispatcher, EventFilter, TestKit}
import com.stulsoft.akka.data.watcher1.StopSystemAfterAll
import com.stulsoft.akka.data.watcher1.actor.DataWatcherActor.NewFile
import com.stulsoft.akka.data.watcher1.actor.DataWatcherActorTest._
import com.stulsoft.akka.data.watcher1.service.DirectoryWatcher
import com.typesafe.config.ConfigFactory
import org.scalamock.scalatest.MockFactory
import org.scalatest.{MustMatchers, WordSpecLike}

/** Unit tests for DataWatcherActor
  *
  * @author Yuriy Stul
  */
class DataWatcherActorTest extends TestKit(testSystem)
  with MockFactory
  with WordSpecLike
  with MustMatchers
  with StopSystemAfterAll {
  "DataWatcherActor" must {
    "create new instance" in {
      val fakedDirectoryWatcherService = stub[DirectoryWatcher]
      val directoryWatcherActorProps = DataWatcherActor.props(fakedDirectoryWatcherService)
      system.actorOf(directoryWatcherActorProps, "data-watcher-1")
      expectNoMsg()
    }

    "preStart executes watcher.watch" in {
      val fakedDirectoryWatcherService = mock[DirectoryWatcher]
      (fakedDirectoryWatcherService.watch _).expects(*).once()
      val directoryWatcherActorProps = DataWatcherActor.props(fakedDirectoryWatcherService)
      system.actorOf(directoryWatcherActorProps, "data-watcher-2")
      expectNoMsg()
    }

    "preStart executes watcher.watch with right parameter" in {
      val fakedDirectoryWatcherService = mock[DirectoryWatcher]
      val directoryWatcherActorProps = DataWatcherActor.props(fakedDirectoryWatcherService)
      val directoryWatcherActor = system.actorOf(directoryWatcherActorProps, "data-watcher-3")
      (fakedDirectoryWatcherService.watch _).expects(directoryWatcherActor).once()
      expectNoMsg()
    }

    "receive should handle message NewFile" in {
      val fakedDirectoryWatcherService = stub[DirectoryWatcher]
      val directoryWatcherActorProps = DataWatcherActor.props(fakedDirectoryWatcherService).withDispatcher(CallingThreadDispatcher.Id)
      val directoryWatcherActor = system.actorOf(directoryWatcherActorProps, "data-watcher-4")
      val msg = NewFile("test path", "test file name")
      EventFilter.info(message = "Created test file name file in test path", occurrences = 1).intercept {
        directoryWatcherActor ! msg
      }
      expectNoMsg()
    }
  }
}

object DataWatcherActorTest {
  // Creates a system with a configuration that attaches a test event listener
  val testSystem: ActorSystem = {
    val config = ConfigFactory.parseString(
      """
         akka.loggers = [akka.testkit.TestEventListener,akka.event.slf4j.Slf4jLogger]
      """)
    ActorSystem("testSystem", config)
  }
}