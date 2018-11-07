/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.queues

/**
  * @author Yuriy Stul
  */
object Queue1Runner extends App {
  test1()

  def test1(): Unit = {
    println("==>test1")
    (1 to 10).foreach(i => Queue1.add(SomeObject(i, s"text $i")))

    var someObject: Option[SomeObject] = Queue1.get()
    while (someObject.isDefined) {
      println(someObject)
      someObject = Queue1.get()
    }

    println("<==test1")
  }
}
