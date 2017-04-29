/*
 * Copyright (c) 2016. Yuriy Stul
 */

package com.stulsoft.pspark.util

import java.io.{File, PrintWriter}
import java.util.Scanner

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.FiniteDuration
import scala.concurrent.duration._

/**
  * Created by Yuriy Stul on 12/5/2016.
  */
object DataProvider {
  /**
    * Copies source data to specified directory.
    *
    * The method splits source file and creates data files in specified directory.
    *
    * @param source               specifies path to file with source data
    * @param destinationDirectory specified destination directory
    * @param maxLines             maximum number of lines in each file
    * @param interval             intervel between creating fils
    * @return Future[Unit]
    */
  def copyData(source: String, destinationDirectory: String, maxLines: Int, interval: FiniteDuration): Future[Unit] = {
    println("==>copyData")
    require(source != null, "source couldn't be null")
    require(destinationDirectory != null, "destinationDirectory couldn't be null")
    require(maxLines > 0, "maxLines couldn't be null and must be greater than 0")
    require(interval != null, "interval couldn't be null")

    val sourceFile = new File(source)
    if (!sourceFile.exists) {
      throw new IllegalArgumentException(s"Source file $source doesn't exist, nothing to do.")
    }

    val dstDirFile = new File(destinationDirectory)
    if (!dstDirFile.exists) {
      if (!dstDirFile.mkdirs) {
        throw new RuntimeException(s"Can't create $destinationDirectory directory")
      }
    } else if (dstDirFile.exists && dstDirFile.isFile) {
      throw new IllegalArgumentException(s"Destination directory $destinationDirectory points to existing file. Can't continue.")
    }

    import scala.concurrent.ExecutionContext.Implicits.global
    Future {
      // Define number of chunks
      val in = new Scanner(sourceFile)
      var chunkCounter = 1
      var lineCounter = 0
      while (in.hasNext) {
        in.nextLine
        lineCounter += 1
        if (lineCounter >= maxLines) {
          chunkCounter += 1
          lineCounter = 0
        }
      }
      in.close()

      // Create tasks to copy files
      val taskFutures =
        (1 to chunkCounter).map(chunk => {
          Future {
            if (chunk > 1) {
              println(s"wait ${(chunk - 1) * interval.toMillis}")
              Thread.sleep((chunk - 1) * interval.toMillis)
            }
            val in = new Scanner(sourceFile)
            val out = new PrintWriter(new File(destinationDirectory, s"f$chunk.txt"))
            var count = maxLines
            var start = (chunk - 1) * maxLines
            while (in.hasNext && {
              start -= 1
              start >= 0
            })
              in.nextLine

            while (in.hasNextLine && {
              count -= 1
              count >= 0
            }) {
              val line = in.nextLine()
              out.println(line)
            }
            println(s"Wrote file number $chunk")
            out.close()
            in.close()
          }
        })

      val job = Future sequence taskFutures

      // Wait all tasks
      Await.result(job, (interval * chunkCounter) + 1.seconds)
    }
  }
}
