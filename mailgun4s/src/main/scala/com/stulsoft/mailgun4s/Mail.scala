/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.mailgun4s

/**
  * @author Yuriy Stul
  */
case class Mail(from: String, to: String, cc: Option[String], subject: String, body: MailBody)

trait MailBody {
  val bodyType: String
  val bodyContent: String
}

case class TextBody(body: String) extends MailBody {
  override val bodyType: String = "text"
  override val bodyContent: String = body
}

case class HtmlBody(body: String) extends MailBody {
  override val bodyType: String = "html"
  override val bodyContent: String = body
}

object Ttt extends App {
  val mText = Mail("from", "to", None, "subject", TextBody("textBody"))
  println(mText.body.bodyType)
  val mHtml = Mail("from", "to", None, "subject", HtmlBody("htmlBody"))
  println(mHtml.body.bodyType)
}