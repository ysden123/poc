/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.mailgun4s

import org.scalatest.{FlatSpec, Matchers}

/**
  * Unit tests for TextBody class
  *
  * @author Yuriy Stul
  */
class TextBodyTest extends FlatSpec with Matchers {
  behavior of "TextBody"
  "bodyType" should "return [text]" in {
    TextBody("tttt").bodyType shouldBe "text"
  }

  "bodyContent" should "return the body content" in {
    val content = "ttt"
    TextBody(content).bodyContent shouldBe content
  }
}
