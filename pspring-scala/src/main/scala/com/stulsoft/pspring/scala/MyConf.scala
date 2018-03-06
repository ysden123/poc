package com.stulsoft.pspring.scala

import javax.inject.Inject

import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

/**
  * @author Yuriy Stul
  * @since 3/6/2018
  */
@Component
class MyConf @Inject()(var env: Environment) {
  def test(): Unit = {
    println("==>test")
    println(s"""env.getProperty("test.test1") = ${env.getProperty("test.test1")}""")
    env.getProperty("test.test1")
  }
}
