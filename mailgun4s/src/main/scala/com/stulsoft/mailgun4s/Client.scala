/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.mailgun4s

import org.apache.http.auth.{AuthScope, UsernamePasswordCredentials}
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.{CloseableHttpResponse, HttpPost}
import org.apache.http.impl.client.{BasicCredentialsProvider, HttpClients}
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
import org.apache.http.{HttpStatus, NameValuePair}

/**
  * @author Yuriy Stul
  */
class Client {
  private lazy val conf = new Config

  def sendMail(mail: Mail): Unit = {
    val url = conf.urlToSendMessage.format(conf.apiVersion, conf.domain)
    val post = new HttpPost(url)

    val credentials = new UsernamePasswordCredentials("api", conf.apiKey)
    val credentialsProvider = new BasicCredentialsProvider()
    credentialsProvider.setCredentials(new AuthScope("api.mailgun.net", 443), credentials)
    val httpClient = HttpClients.custom().setDefaultCredentialsProvider(credentialsProvider).build()

    val params = new java.util.ArrayList[NameValuePair]()
    params.add(new BasicNameValuePair("from", mail.from))
    params.add(new BasicNameValuePair("to", mail.to))
    mail.cc.foreach(cc => params.add(new BasicNameValuePair("cc", cc)))
    params.add(new BasicNameValuePair("subject", mail.subject))
    params.add(new BasicNameValuePair(mail.body.bodyType, mail.body.bodyContent))

    post.setEntity(new UrlEncodedFormEntity(params))

    var response: CloseableHttpResponse = null
    try {
      response = httpClient.execute(post)
      println(s"response.setStatusCode: ${response.getStatusLine}")

      if (response.getStatusLine.getStatusCode == HttpStatus.SC_OK) {
        val entity = response.getEntity
        println(s"Response: ${EntityUtils.toString(entity)}")
        EntityUtils.consume(entity)
      }
    }
    catch {
      case e: Exception => println(e.getMessage)
    }
    finally {
      if (response != null)
        response.close()
    }
  }
}

object tt extends App {
  val m = Mail("ysden123@gmail.com", "ysden123@gmail.com", None, "test 3", HtmlBody("<htm><h1>Header</h1>text</html>"))
  new Client sendMail m
}
