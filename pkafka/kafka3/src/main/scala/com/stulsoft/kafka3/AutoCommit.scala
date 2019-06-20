/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.kafka3


/**
  * @author Yuriy Stul
  */
object AutoCommit extends Enumeration {
  type AutoCommit = Value
  val EnabledAutoCommit: Value = Value("true")
  val DisabledAutoCommit: Value = Value("false")
}
