package com.stulsoft.akka.data.watcher1.service

import java.io.{File, PrintWriter}
import java.nio.file.Paths

import org.apache.commons.io.FileUtils

/** Test data generator
  *
  * @author Yuriy Stul.
  */
object DataGenerator {
  /**
    * Deletes all sources and creates empty sources (directories)
    *
    * @param sources list of directories
    */
  def createSourceDirectories(sources: List[String]): Unit = {
    sources.foreach(source => {
      val path = Paths.get(source)
      val file = new File(path.toUri)
      FileUtils.deleteQuietly(file)
      file.mkdir()
    })
  }

  /**
    * Writes specified number of a test lines
    *
    * @param directory     directory
    * @param name          file name
    * @param numberOfLines number of lines
    */
  def generateTextFile(directory: String, name: String, numberOfLines: Int): Unit = {
    val writer = new PrintWriter(new File(directory, name))
    (1 to numberOfLines).foreach(i => writer.println(s"test line # $i"))
    writer.close()
  }
}