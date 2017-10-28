/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.data.watcher1.actor

import akka.actor.ActorSystem
import akka.testkit.TestKit
import com.stulsoft.akka.data.watcher1.StopSystemAfterAll
import com.stulsoft.akka.data.watcher1.service.DirectoryWatcher
import org.scalamock.scalatest.MockFactory
import org.scalatest.{MustMatchers, WordSpecLike}

/** Unit tests for DataWatcherActor
  *
  * @author Yuriy Stul
  */
class DataWatcherActorTest extends TestKit(ActorSystem("testsystem"))
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
  }
}