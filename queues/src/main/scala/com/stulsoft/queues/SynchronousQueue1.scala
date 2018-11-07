/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.queues

import java.util.concurrent.SynchronousQueue

/**
  * @author Yuriy Stul
  */
object SynchronousQueue1 {
  private val queue = new SynchronousQueue[SomeObject]()

  def add(someObject: SomeObject): Unit = {
    queue.put(someObject)
  }

  def get(): Option[SomeObject] = {
    Option(queue.poll())
  }

  def size(): Int={
    queue.size()
  }
}
