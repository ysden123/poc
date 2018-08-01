/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.json.spray

import spray.json.DefaultJsonProtocol._
import spray.json._ // if you don't supply your own Protocol (see below)

/** Without protocol
  * See [[https://github.com/spray/spray-json Usage]]
  *
  * @author Yuriy Stul
  */
object SprayJsonSimple extends App {
  val source = """{ "some": "JSON source" }"""
  val jsonAst = source.parseJson // or JsonParser(source)
  println(s"jsonAst: $jsonAst")

  val json = jsonAst.prettyPrint // or .compactPrint
  println(s"json: $json")

  // Convert any Scala object to a JSON AST using the toJson extension method
  val jsonAst2 = List(1, 2, 3).toJson
  println(s"jsonAst2: $jsonAst2")

  //  // Convert a JSON AST to a Scala object with the convertTo method
  //  val myObject = jsonAst2.convertTo[MyObjectType]
}
