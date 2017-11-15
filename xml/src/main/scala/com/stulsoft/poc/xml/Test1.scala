/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.poc.xml

/** Playing with standard Scala XML
  *
  * @author Yuriy Stul
  */
object Test1 extends App {
  test1()
  test2()

  def test1(): Unit = {
    println("==>test1")
    val books = <books>
      <book id="b1615">Don Quixote</book>
      <book id="b1867">War and Peace</book>
    </books>
    val titles = (books \ "book").map(_.text).toList
    println(s"titles: $titles")

    val titlesWithId = (books \ "book").filter(x => x.attribute("id").isDefined)
      .map(b => (b.attribute("id").get, b.text))
    println(s"titlesWithId: $titlesWithId")

    val titlesWithId2 = (books \ "book").filter(book => (book \@ "id").nonEmpty)
      .map(book => (book \@ "id", book.text))
    println(s"titlesWithId2: $titlesWithId2")

    println("<==test1")
  }

  def test2(): Unit = {
    println("==>test2")
    val books = <books>
      <book id="b1615">Don Quixote</book>
      <book id="b1867">War and Peace</book>
    </books>
    val ids = (books \ "book").map(book => book \@ "err").filter(x => x.nonEmpty)
    println(s"ids: $ids")
    println("<==test2")
  }
}
