/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.rest

import javax.inject.Inject
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

/**
  * @author Yuriy Stul
  */
@RestController
class Api {
  private val logger = LoggerFactory.getLogger(classOf[Api])

  @Inject
  var someService: SomeService = _

  @RequestMapping(Array("/foo"))
  def foo(): String = {
    someService.foo()
    return "Done foo"
  }
}
