/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.pscalacheck.myclasses

import org.scalacheck.Prop.forAll
import org.scalacheck.{Gen, Properties}

/** Checks Converter1 class - ScalaCheck style
  *
  * @author Yuriy Stul
  */
object Converter1Test extends Properties("Converter1") {
  // All symbols
  property("toInt for all") = forAll { x: String => Converter1(x).toInt == x.length }

  // ASCII symbols
  private val usAsciiStringGen = Gen.containerOf[Array, Char](Gen.choose[Char](0, 127)).map(_.mkString)
  property("toInt for ASCII") = forAll(usAsciiStringGen) { x: String => Converter1(x).toInt == x.length }
}
