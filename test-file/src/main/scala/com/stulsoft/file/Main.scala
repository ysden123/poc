package com.stulsoft.file

/**
  * @author Yuriy Stul.
  */
object Main extends App {
test()
  def test(): Unit ={
    val fn = "resourceMain.txt"
    val source = Utils.source(fn)
    println("got source")
    println(s"source length in lines is ${source.getLines().toList.length}")
    source.close()
  }
}
