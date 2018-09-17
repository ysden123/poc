/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.pom.experiments.application.app4

import javax.inject.Inject
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.core.env.AbstractEnvironment

/**
  * @author Yuriy Stul
  */
@SpringBootApplication
class App4Application extends CommandLineRunner {
  @Inject
  private var environment: AbstractEnvironment = _

  override def run(args: String*): Unit = {
    println("==>run App4Application")
    println("spring.config.location: " + System.getProperty("spring.config.location"))
    val envIter = environment.getPropertySources.iterator()
    println("Prop sources:")
    environment.getPropertySources.forEach(propSource => {
      println(s"Prop source name: ${propSource.getName}")
    })
    println(s"""environment.getProperty("test.name"): ${environment.getProperty("test.name")} """)

    println("<==run")
  }
}
