/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.queues

import scala.collection.mutable

/**
  * @author Yuriy Stul
  */
object Queue2 {
  private val queue = mutable.Queue.empty[SomeObject]
  private val lock: Object = new Object

  def add(someObject: SomeObject): Unit = {
    lock.synchronized {
      queue += someObject
    }
  }

  def get(): Option[SomeObject] = {
    lock.synchronized {
      if (queue.isEmpty)
        None
      else
        Some(queue.dequeue())
    }
  }
}
