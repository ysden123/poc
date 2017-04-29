/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.mailgun4s

import org.scalatest.{FlatSpec, Matchers}

/**
  * Unit tests for HtmlBody class
  *
  * @author Yuriy Stul
  */
class HtmlBodyTest extends FlatSpec with Matchers {
  behavior of "HtmlBody"
  "bodyType" should "return [text]" in {
    HtmlBody("tttt").bodyType shouldBe "html"
  }

  "bodyContent" should "return the body content" in {
    val content = "ttt"
    HtmlBody(content).bodyContent shouldBe content
  }
}
