/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.pscalacheck.myclasses

/** A class for testing.
  *
  * @author Yuriy Stul
  */
case class Converter1(name: String) {
  /** Converts to name length
    *
    * @return the name length
    */
  def toInt: Int = {
//    println(s"name is $name")
    name.length
  }
}
