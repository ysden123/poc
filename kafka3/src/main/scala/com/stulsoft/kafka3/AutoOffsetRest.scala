/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.kafka3

/**
  * @author Yuriy Stul
  */
object AutoOffsetRest extends Enumeration {
  type AutoOffsetRest = Value
  val Earliest: Value = Value("earliest")
  val Latest: Value = Value("latest")
  val None: Value = Value("none")
}
