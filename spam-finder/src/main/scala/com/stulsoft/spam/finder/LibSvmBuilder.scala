/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.spam.finder

import scala.io.Source

/**
  * @author Yuriy Stul
  */
object LibSvmBuilder {
  def build(path: String): Set[(String, Int)] = {
    val src = Source.fromFile(Utils.getResourceFilePath(path))
    val lines = src.getLines()

    val data = lines.flatMap(line => line.split("\\s+")
      .map(_.toLowerCase))
      .map(_.replaceAll(",", ""))
      .toSet[String].zipWithIndex
    src.close()
    data
  }
}

object LibSvmBuilderTest extends App {
  test()

  def test(): Unit = {
    val data = LibSvmBuilder.build("training.txt")

    data.find(e => e._1 == "viagra") match {
      case Some((word, index)) => println(s"Found word $word with index $index")
      case None => println("No word found")
    }

    data.find(e => e._2 == 6) match {
      case Some((word, index)) => println(s"Found word $word with index $index")
      case None => println("No word found")
    }

    data.find(e => e._2 == 0) match {
      case Some((word, index)) => println(s"Found word $word with index $index")
      case None => println("No word found")
    }

    data.find(e => e._2 == 10) match {
      case Some((word, index)) => println(s"Found word $word with index $index")
      case None => println("No word found")
    }
    println(s"data: $data")
  }
}
