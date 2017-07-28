/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.pscalacheck.myclasses

import org.scalacheck.Prop.forAll
import org.scalacheck.Properties

/** Checks Converter1 class
  *
  * @author Yuriy Stul
  */
object Converter1Test extends Properties("Converter1") {
  property("toInt") = forAll { x: String => Converter1(x).toInt() == x.length }
}
