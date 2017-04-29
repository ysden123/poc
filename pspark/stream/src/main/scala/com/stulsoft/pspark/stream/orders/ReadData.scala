/*
 * Copyright (c) 2016. Yuriy Stul
 */

package com.stulsoft.pspark.stream.orders

import java.sql.Timestamp
import java.text.SimpleDateFormat

import com.stulsoft.pspark.stream.Util

import scala.concurrent.duration._

/**
  * Reads stream data
  *
  * @author Yuriy Stul
  */
object ReadData extends App {
  println("==>ReadData")
  val stream = Util.getStreamingContext(appName, "local[4]")
  println("Created Streaming Context")

  val fileStream = stream.textFileStream(inputDataDir)
  val orders = fileStream.flatMap(line => {
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
    val s = line.split(",")
    try {
      assert(s(6) == "B" || s(6) == "S")
      List(Order(new Timestamp(dateFormat.parse(s(0)).getTime), s(1).toLong, s(2).toLong, s(3), s(4).toInt,
        s(5).toDouble, s(6) == "B"))
    }
    catch {
      case e: Throwable => println(s"Wrong line format ($e): $line")
        List()
    }
  })

  val numPerType = orders.map(o => (o.buy, 1L)).reduceByKey((c1, c2) => c1 + c2)
  numPerType.repartition(1).saveAsTextFiles(resultPrefix, resultSuffix)

  println("Start")
  stream.start()
  stream.awaitTerminationOrTimeout((3 minutes).toMillis)
  stream.stop()
  println("<==ReadData")
}
