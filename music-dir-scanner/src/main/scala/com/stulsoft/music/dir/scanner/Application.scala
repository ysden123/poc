/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.music.dir.scanner

import java.io.File

import com.typesafe.scalalogging.LazyLogging

/**
  * @author Yuriy Stul
  */
object Application extends App with LazyLogging {

  val srcDir = new File("e:\\Caro Emerald\\")
  val files = fileList(srcDir)
  /*
    files
      .filter(_.isFile)
      .map(file => FileInfo(file.getParent, file.getName))
      .foreach(fi => logger.info(s"${fi.toString} - ${fi.clearFileName()}"))
  */

  def duplicatedDirs(fileInfos: Iterable[FileInfo]): String = {
    fileInfos.map(fileInfo => fileInfo.directory).mkString(", ")
  }

  files
    .filter(_.isFile)
    .map(file => FileInfo(file.getParent, file.getName))
    .groupBy(fi => fi.clearFileName())
    .filter { case (_, fileInfoList) => fileInfoList.length > 1 }
    .foreach { case (name, fileInfoList) => logger.info(s""""$name" was duplicated ${fileInfoList.length} times in ${duplicatedDirs(fileInfoList)}""") }


  def fileList(file: File): Array[File] = {
    val these = file.listFiles
    these ++ these.filter(_.isDirectory).flatMap(fileList)
  }
}
