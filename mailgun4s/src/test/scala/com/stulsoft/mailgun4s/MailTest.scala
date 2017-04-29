/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.mailgun4s

import org.scalatest.{FlatSpec, Matchers}

/**
  * Unit tests for Mail class
  *
  * @author Yuriy Stul
  */
class MailTest extends FlatSpec with Matchers {
  behavior of "Mail"
  "constructor" should "allow usage the text body" in {
    Mail("from", "to", None, "subject", TextBody("textBody")) should not be null
  }
  it should "allow usage the html body" in {
    Mail("from", "to", None, "subject", HtmlBody("htmlBody")) should not be null
  }
}
