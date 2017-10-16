package com.stulsoft.file

import scala.util.{Failure, Success}

/**
  * @author Yuriy Stul.
  */
object Main extends App {
  testRunner("resourceMain.txt")
  testRunner("nonResource.txt")
  testRunner("ERRORResource.txt")

  def testRunner(fn:String): Unit ={
    println("==>testRunner")
    println(s"Use file $fn")
    Utils.source(fn) match {
      case Success(source) =>
        println("got source")
        println(s"source length in lines is ${source.getLines().toList.length}")
        source.close()
      case Failure(e) =>
        println(s"Failed getting source: ${e.getMessage}")
    }
    println("<==testRunner")
  }
}
