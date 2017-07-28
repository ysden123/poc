/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.pscalacheck.demo

import org.scalacheck.Prop.forAll
import org.scalacheck.Properties

/** Minimal example sbt project
  *
  * @see [[https://github.com/rickynils/scalacheck/blob/master/examples/simple-sbt/src/test/scala/Demo.scala original code]]
  * @author Yuriy Stul
  */
object ScalaCheckDemo extends Properties("Demo") {

  property("myprop") = forAll { l: List[Int] =>
    l.reverse.reverse == l
  }

}