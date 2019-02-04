/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.poc.scala.jmx

/**
  * @author Yuriy Stul
  */
class Hello(var message: String) extends HelloMBean {

  def this() {
    this("Hello, world")
  }

  override def setMessage(message: String): Unit = this.message = message

  override def getMessage(): String = message

  override def sayHello: Unit = {
    println(message)
  }
}
