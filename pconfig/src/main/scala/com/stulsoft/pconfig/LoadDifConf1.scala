package com.stulsoft.pconfig

import com.typesafe.config.ConfigFactory

/**
  * @author Yuriy Stul.
  */
object LoadDifConf1 extends App {
  test1()
  def test1(): Unit ={
    println("==>test1")
    System.setProperty("config.resource","conf1.conf")
    val conf = ConfigFactory.load()
    println(s"""${conf.getString("test.p")}""")
    println("<==test1")
  }
}
