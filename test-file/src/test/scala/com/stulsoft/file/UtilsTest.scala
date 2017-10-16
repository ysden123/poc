package com.stulsoft.file

import org.scalatest.{FlatSpec, Matchers}

import scala.util.{Failure, Success}

/**
  * @author Yuriy Stul.
  */
class UtilsTest extends FlatSpec with Matchers {

  behavior of "UtilsTest"

  "resourceName" should "return file path" in {
    val fn = "resourceTest.txt"
    Utils.source(fn) match {
      case Success(source) =>
        source.getLines().toList.length > 0 shouldBe true
        source.close()
      case Failure(e) =>
        fail(e.getMessage)
    }
  }
}
