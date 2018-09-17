/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.rest

import javax.inject.Inject
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContext
import org.springframework.core.env.Environment
import org.springframework.scheduling.annotation.{EnableScheduling, Scheduled}

/**
  * @author Yuriy Stul
  */
@SpringBootApplication
@EnableScheduling
class ApplicationConfiguration @Inject()(env: Environment, ctx: ApplicationContext) extends CommandLineRunner {
  private val logger = LoggerFactory.getLogger(classOf[ApplicationConfiguration])

  @Inject
  var someService: SomeService = _

  @Scheduled(cron = "*/10 * * * * *")
  def makeFoo(): Unit = {
    someService.foo()
  }

  override def run(args: String*): Unit = {
    logger.info("==>run")
    logger.info("<==run")
  }
}
