/*
 * Copyright (c) 2016. Yuriy Stul
 */

package com.stulsoft.pspark.stream.orders

import java.io.{File, PrintWriter}
import java.util.Calendar

import com.stulsoft.pspark.util.DataProvider

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.Random

/**
  * Writes the stream data
  *
  * @author Yuriy Stul
  */
object WriteData extends App {
  /**
    * Generates test data
    *
    * @param lineNumber number of lines
    * @param outputFile output file name
    */
  def generateData(lineNumber: Int, outputFile: String): Unit = {
    val writer: PrintWriter = new PrintWriter(new File(outputFile))
    for (i <- 1 to lineNumber) {
      val orderTimestamp: String = getDate
      val orderId: Int = i
      val clientId: Int = Random.nextInt(100) + 1
      val stockSymbol: String = getSymbol
      val numberOfStocks: Int = Random.nextInt(1000) + 1
      val price: Double = Random.nextDouble() * 100.0
      val status: Char = if (Random.nextBoolean())
        'B'
      else
        'S'

      writer.println(s"$orderTimestamp,$orderId,$clientId,$stockSymbol,$numberOfStocks,$price,$status")
    }
    writer.close()
  }

  private def getSymbol: String = {
    val start: Char = 'A'
    val end: Char = 'Z'
    val length = end - start
    val first: Char = (start + Random.nextInt(length)).toChar
    val second: Char = (start + Random.nextInt(length)).toChar
    s"$first$second"
  }

  private def getDate: String = {
    val now: Calendar = Calendar.getInstance
    var result = s"${now.get(Calendar.YEAR)}-${now.get(Calendar.MONTH)}-${now.get(Calendar.DAY_OF_MONTH)}"
    result += s" ${Random.nextInt(24)}:${Random.nextInt(60)}:${Random.nextInt(60)}"
    result
  }

  println("==>WriteData")

  val inputName="inputStream.txt"
  println("Create input stream file")
  generateData(10000, inputName)
  println("Created input stream file")

  println("Copy parts of the input data")
  val f =DataProvider.copyData(inputName, inputDataDir, 1000, 3 seconds)
  Await.ready(f, 2 minutes)

  println("<==WriteData")
}
