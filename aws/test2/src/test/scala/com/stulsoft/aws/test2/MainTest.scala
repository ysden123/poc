/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.aws.test2

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Yuriy Stul
  */
class MainTest extends FlatSpec with Matchers {
  behavior of "Main"

  "greeting" should "return text" in {
    val input = new ByteArrayInputStream(
      """
        |{
        |  "firstName": "Robert 13",
        |  "lastName": "Dole 65432"
        |}
      """.stripMargin.getBytes())
    val output = new ByteArrayOutputStream()

    Main.greeting(input, output)
    output.toString shouldBe "Greetings Robert 13 Dole 65432."
  }
}
