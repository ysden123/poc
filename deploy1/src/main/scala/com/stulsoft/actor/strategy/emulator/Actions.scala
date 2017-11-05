/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.actor.strategy.emulator

import scala.concurrent.duration.Duration

/**
  * @author Yuriy Stul
  */
object Actions {

  case class Delay(time: Duration)

  case class Exception(exception: Throwable)

}
