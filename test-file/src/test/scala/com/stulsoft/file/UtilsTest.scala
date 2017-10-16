package com.stulsoft.file

import org.scalatest.{FlatSpec, Matchers}

import scala.util.{Failure, Success}

/** Unit tests for Utils
  *
  * @author Yuriy Stul.
  */
class UtilsTest extends FlatSpec with Matchers {

  behavior of "Utils"

  "source" should "return source from resource" in {
    val fn = "resourceTest.txt"
    Utils.source(fn) match {
      case Success(source) =>
        source.getLines().toList.nonEmpty shouldBe true
        source.close()
      case Failure(e) =>
        fail(e.getMessage)
    }
  }

  it should "return source from non resource" in {
    val fn = "nonResource.txt"
    Utils.source(fn) match {
      case Success(source) =>
        source.getLines().toList.nonEmpty shouldBe true
        source.close()
      case Failure(e) =>
        fail(e.getMessage)
    }
  }
}
