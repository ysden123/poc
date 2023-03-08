/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.json.json4s

import org.json4s._
import org.json4s.jackson.Serialization.read

/** Parses JSON using ''read''
 *
 * @author Yuriy Stul
 */
object ParserWithRead {
  def main(args: Array[String]): Unit = {
    println("==>ParserWithRead")
    implicit val formats: DefaultFormats = DefaultFormats
    val theObject = read[TheObject]("""{"name":"some name", "age":123, "height":123.45}""")
    println(theObject)

    val theObjectCollection = read[List[TheObject]]("""[{"name":"some name", "age":123, "height":123.45},{"name":"some name 2", "age":456, "height":321.2}]""")
    println(theObjectCollection)

    val theObjectWithOption1 = read[TheObjectWithOption](StreamInput(getClass.getClassLoader.getResourceAsStream("withOption1.json")))
    println(theObjectWithOption1)

    val theObjectWithOption2 = read[TheObjectWithOption](StreamInput(getClass.getClassLoader.getResourceAsStream("withOption2.json")))
    println(theObjectWithOption2)

    val choice = read[Choice]("""{"text":"some text"}""")
    println(choice)
  }

  //
  //  val theObjectCollection2 = forList[TheObject]("""[{"name":"some name", "age":123, "height":123.45},{"name":"some name 2", "age":456, "height":321.2}]""")
  //  println(theObjectCollection2)
  //
  //  def forList[A <: AnyRef](json: String): List[A] = {
  //    read[List[A]](json)(formats, Manifest[List[A]])
  //  }
}
