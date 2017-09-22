/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.spam.finder

import java.io.File

/**
  * @author Yuriy Stul
  */
object Utils {
  /**
    * Returns an absolute path to file
    *
    * @param name specifies resource file; may include subdirectory
    * @return the absolute path to file
    */
  def getResourceFilePath(name: String): String = new File(getClass.getClassLoader.getResource(name).toURI)
    .getAbsolutePath
}
