package com.stulsoft.file

import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Yuriy Stul.
  */
class UtilsTest extends FlatSpec with Matchers {

  behavior of "UtilsTest"

  "resourceName" should "return file path" in {
    val fn = "resourceTest.txt"
    val source = Utils.source(fn)
    source.getLines().toList.length > 0 shouldBe true
    source.close()
  }
}
