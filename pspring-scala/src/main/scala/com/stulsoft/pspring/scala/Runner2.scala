package com.stulsoft.pspring.scala

import org.springframework.boot.SpringApplication

/**
  * @author Yuriy Stul
  * @since 3/6/2018
  */
object Runner2 {
  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[Application2], args: _*).close()
  }
}
