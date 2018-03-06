package com.stulsoft.pspring.scala

import javax.inject.Inject

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.core.env.AbstractEnvironment

/**
  * @author Yuriy Stul
  * @since 3/6/2018
  */
@SpringBootApplication
class Application2 extends CommandLineRunner {
  @Inject
  private var environment: AbstractEnvironment = _

  override def run(strings: String*): Unit = {
    println("==>run")
    println(s"""environment.getProperty("test.test1")=${environment.getProperty("test.test1")}""")
    environment.getProperty("test.test1")

    println("<==run")
  }
}
