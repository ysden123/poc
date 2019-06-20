package com.stulsoft.pkafka.scala

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Executes producer and consumer.
  *
  * @author Yuriy Stul
  */
object TestRunnerScala extends App {
  // Start consumer
  Future {
    Consumer1.readMessages(2)
  }

  // Start producer
  Future {
    Producer1.sendMessages()
  }

  try {
    Thread.sleep(5000)
    System.exit(0)
  }
  catch {
    case e: InterruptedException => e.printStackTrace()
  }
}
