/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.rest

import javax.inject.Inject
import org.slf4j.LoggerFactory
import org.springframework.core.env.{ConfigurableEnvironment, PropertySource}
import org.springframework.stereotype.Service

/**
  * @author Yuriy Stul
  */
@Service
class SomeService @Inject()(env: ConfigurableEnvironment) {
  private val logger = LoggerFactory.getLogger(classOf[SomeService])

  def foo(): Unit = {
    val propertySources = env.getPropertySources.iterator()
    while (propertySources.hasNext) {
      logger.info(s"Property source: ${propertySources.next().getName}")
    }
  }
}
