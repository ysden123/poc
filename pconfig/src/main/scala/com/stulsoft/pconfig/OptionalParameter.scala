package com.stulsoft.pconfig

import com.typesafe.config.ConfigFactory

/**
  * @author Yuriy Stul.
  */
object OptionalParameter extends App {
  test1()
  test2()

  def test1(): Unit = {
    println("==>test1")
    val conf = ConfigFactory.load("port1.conf")
    println(s"""port=${conf.getInt("test.port")}""")
    println("<==test1")
  }

  def test2(): Unit = {
    println("==>test2")
    val conf = ConfigFactory.load("port2.conf")
    println(s"""port=${conf.getInt("test.port")}""")
    println("<==test2")
  }
}
