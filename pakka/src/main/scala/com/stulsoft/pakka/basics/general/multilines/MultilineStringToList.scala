package com.stulsoft.pakka.basics.general.multilines

/**
  * Created by Yuriy Stul on 10/7/2016.
  *
  * Original code: http://alvinalexander.com/scala/scala-class-object-function-convert-multiline-string-to-list-seq
  */
object Q {

  def apply(s: String): Seq[String] = s.split("\n")
    .toSeq
    .map(_.trim)
    .filter(_ != "")
}

object MultilineStringToList extends App {
val t = Q(
  """
    line 1
    line 2
    line 3
  """)
  t foreach println
}
