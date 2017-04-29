package com.stulsoft.pscalatest.prod

/**
  * Created by Yuriy Stul on 9/24/2016.
  */
class Prod1(var p1: Int, var p2: String) {
  override def toString: String = s"p1 = $p1, p2: $p2"

  def incrementP1(inc: Int): Unit = p1 += inc
}

object Prod1 {
  def apply(p1: Int, p2: String): Prod1 = {
    new Prod1(p1, p2)
  }
}
