/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.queues

import java.util.concurrent.ConcurrentLinkedQueue

/**
  * @author Yuriy Stul
  */
object ConcurrentLinkedQueue1 {
  private val queue = new ConcurrentLinkedQueue[SomeObject]()

  def add(someObject: SomeObject): Unit = {
    queue.add(someObject)
  }

  def get(): Option[SomeObject] = {
    Option(queue.poll())
  }

  def size(): Int={
    queue.size()
  }
}
