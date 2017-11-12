package com.stulsoft.pconfig

import com.typesafe.config.ConfigFactory

/**
  * @author Yuriy Stul.
  */
object LoadDifConf2 extends App {
  test2()
  def test2(): Unit ={
    println("==>test2")
    System.setProperty("config.resource","conf2.conf")
    ConfigFactory.empty()
    val conf = ConfigFactory.load()
    println(s"""${conf.getString("test.p")}""")
    println("<==test2")
  }
}
