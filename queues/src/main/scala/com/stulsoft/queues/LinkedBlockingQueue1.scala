/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.queues

import java.util.concurrent.LinkedBlockingQueue


/**
  * @author Yuriy Stul
  */
object LinkedBlockingQueue1 {
  private val queue = new LinkedBlockingQueue[SomeObject]()

  def add(someObject: SomeObject): Unit = {
    queue.put(someObject)
  }

  /*
    * BLOCKING get!!!!
    */
  def get(): SomeObject = {
    queue.take()
  }

  def size(): Int = {
    queue.size()
  }
}
