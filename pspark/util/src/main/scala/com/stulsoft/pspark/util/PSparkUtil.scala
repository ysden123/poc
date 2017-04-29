/*
 * Copyright (c) 2016. Yuriy Stul
 */

package com.stulsoft.pspark.util

import java.io.File

/**
  * Created by Yuriy Stul on 11/16/2016.
  */
object PSparkUtil {

  /**
    * Returns an absolute path to file
    *
    * @param name specifies resource file; may include subdirectory
    * @return the absolute path to file
    */
  def getResourceFilePath(name: String): String = new File(getClass.getClassLoader.getResource(name).toURI)
    .getAbsolutePath
}
