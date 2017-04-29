/*
 * Copyright (c) 2016. Yuriy Stul
 */

package com.stulsoft.pspark.util

import java.io.File

import org.apache.commons.io.FileUtils
import org.scalatest.{BeforeAndAfter, FunSpec, Matchers}

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by Yuriy Stul on 12/5/2016.
  */
class DataProvider$Test extends FunSpec with Matchers with BeforeAndAfter {
  describe("DataProvider$Test") {
    val dstDir = "tmpTest"
    describe("#copyData") {
      before {
        val f = new File(dstDir)
        if (f.exists) {
          try {
            FileUtils.cleanDirectory(f)
          }
          catch {
            case e: Exception => println(e.getMessage)
          }
        }
      }
      after {
        val f = new File(dstDir)
        if (f.exists) {
          try {
            FileUtils.cleanDirectory(f)
          }
          catch {
            case e: Exception => println(e.getMessage)
          }
        }
      }
      it("should throw exception for non-existing source file") {
        intercept[IllegalArgumentException] {
          DataProvider.copyData("errorSourceFile", "dst", 10, 5 seconds)
        }
      }
      it("should throw exception for incorrect destination directory") {
        intercept[RuntimeException] {
          DataProvider.copyData(PSparkUtil.getResourceFilePath("test.txt"), ":&$@!//\\ERROR", 10, 5 seconds)
        }
      }
      it("should copy data") {
        val f = DataProvider.copyData(PSparkUtil.getResourceFilePath("test.txt"), dstDir, 10, 5 seconds)
        Await.ready(f, 1 minutes)
        val outDir = new File(dstDir)
        outDir exists() should be(true)
        outDir.list().length should be > 0
      }
    }
  }
}
