/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.poc.scala.jmx

import java.lang.management.ManagementFactory

import com.typesafe.scalalogging.LazyLogging
import javax.management.ObjectName

import scala.io.StdIn

/**
  * @author Yuriy Stul
  */
object SimpleAgent extends App with LazyLogging {
  // Get the platform MBeanServer
  val mbs = ManagementFactory.getPlatformMBeanServer

  // Unique identification of MBeans
  val helloBean = new Hello()
  var helloName: ObjectName = _

  try {
    // Uniquely identify the MBeans and register them with the platform MBeanServer
    helloName = new ObjectName("FOO:name=HelloBean")
    mbs.registerMBean(helloBean, helloName)
  } catch {
    case e: Exception =>
      logger.error(e.getMessage)
  }

  println("SimpleAgent is running...")

  println("Press to continue ...")
  StdIn.readLine()
}
