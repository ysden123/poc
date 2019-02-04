/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.poc.scala.jmx

/**
  * @author Yuriy Stul
  */
trait HelloMBean {
  def setMessage(message: String): Unit
  def getMessage():String
  def sayHello:Unit
}
