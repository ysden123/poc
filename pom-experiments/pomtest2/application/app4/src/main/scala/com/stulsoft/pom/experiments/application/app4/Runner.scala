/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.pom.experiments.application.app4

import org.springframework.boot.SpringApplication

/**
  * @author Yuriy Stul
  */
object Runner {
  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[App4Application], args: _*).close()
  }
}
