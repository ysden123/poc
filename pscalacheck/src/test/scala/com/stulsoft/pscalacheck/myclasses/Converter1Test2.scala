/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.pscalacheck.myclasses

import org.scalacheck.Gen
import org.scalacheck.Prop.forAll
import org.scalatest.FlatSpec
import org.scalatest.prop.Checkers

/** Checks Converter1 class - ScalaTest style
  *
  * @author Yuriy Stul
  */
class Converter1Test2 extends FlatSpec with Checkers {
  behavior of "Converter1"

  "toInt" should "produce length of names for all symbols" in {
    // ScalaTest style
    check((x: String) => Converter1(x).toInt == x.length)
  }
  it should "produce length for ASCII symbols" in {
    // ScalaCheck style
    val usAsciiStringGen = Gen.containerOf[Array, Char](Gen.choose[Char](0, 127)).map(_.mkString)
    forAll(usAsciiStringGen) { x: String => Converter1(x).toInt == x.length }
  }
}
