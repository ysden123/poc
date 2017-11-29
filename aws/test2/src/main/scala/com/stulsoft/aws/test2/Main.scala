/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.aws.test2

case class NameInfo(firstName: String, lastName: String)

/** Lambda function for AWS.
  *
  * Receives HTTP POST request https://API-KEY.execute-api.us-east-2.amazonaws.com/Dev/test2
  * with body:
  * {{{
  * {
  *  "firstName": "Robert 13",
  *  "lastName": "Dole 65432"
  * }
  * }}}
  *
  * Replies with {{{
  *   Greetings Robert 13 Dole 65432.
  * }}}
  *
  * @author Yuriy Stul
  */
object Main {

  import java.io.{InputStream, OutputStream}

  val scalaMapper = {
    import com.fasterxml.jackson.databind.ObjectMapper
    import com.fasterxml.jackson.module.scala.DefaultScalaModule
    new ObjectMapper().registerModule(new DefaultScalaModule)
  }

  def greeting(input: InputStream, output: OutputStream): Unit = {
    val name = scalaMapper.readValue(input, classOf[NameInfo])
    val result = s"Greetings ${name.firstName} ${name.lastName}."
    output.write(result.getBytes("UTF-8"))
  }
}
