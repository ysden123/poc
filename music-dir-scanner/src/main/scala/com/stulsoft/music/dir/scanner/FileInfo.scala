/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.music.dir.scanner

/**
  * @author Yuriy Stul
  */
case class FileInfo(directory: String, name: String) {
  def clearFileName(): String = {
    var clearName = name.substring(3)
    if (clearName.startsWith("-"))
      clearName = clearName.substring(2)
    clearName = clearName.stripLeading()
    clearName = clearName.substring(0, clearName.length - 4)
    clearName
  }
}
