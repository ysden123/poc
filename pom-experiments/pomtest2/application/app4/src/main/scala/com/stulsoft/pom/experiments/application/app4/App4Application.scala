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
class App4Application extends CommandLineRunner{
  @Inject
  private var environment: AbstractEnvironment = _

  override def run(args: String*): Unit = {
    println("==>run App4Application")
    val envIter = environment.getPropertySources.iterator()
    println("Prop sources:")
    while (envIter.hasNext) {
      val propSource = envIter.next()
//      println(propSource.getSource.toString)
      println(s"Prop source name: ${propSource.getName}")
    }
    println(s"""environment.getProperty("test.name"): ${environment.getProperty("test.name")} """)

    println("<==run")
  }
}
