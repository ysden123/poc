/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.queues

import scala.collection.mutable

/**
  * @author Yuriy Stul
  */
object Queue1 {
  private val queue = mutable.Queue.empty[SomeObject]

  def add(someObject: SomeObject): Unit = {
    queue += someObject
  }

  def get(): Option[SomeObject] = {
    if (queue.isEmpty)
      None
    else
      Some(queue.dequeue())
  }
}
