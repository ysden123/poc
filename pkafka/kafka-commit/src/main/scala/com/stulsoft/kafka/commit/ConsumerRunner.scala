/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.kafka.commit

/** Runs the consumer
  *
  * @author Yuriy Stul
  */
object ConsumerRunner extends App {

  val resetMode = menu("Choose reset mode", List((1, "earliest"),
    (2, "latest")))._2

  val autoCommit = menu("Choose auto commit", List((1, "true"),
    (2, "false")))._2

  val commit = menu("Choose commit", List((1, true),
    (2, false)))._2

  println(s"Running consumer with parameters: resetMode = $resetMode, autoCommit = $autoCommit, commit = $commit")


  val consumer = Consumer(resetMode, autoCommit, commit)
  consumer.readMessages()

  private def menu[T](prompt: String, choice: Seq[(Int, T)]): (Int, T) = {
    println(prompt)
    choice.foreach(c => println(s"${c._1} - ${c._2}"))
    val answer = Console.in.readLine().toInt
    val result = choice.find(c => c._1 == answer)
    if (result.isEmpty) {
      println(s"Wrong answer: $answer")
      System.exit(1)
    }
    result.get
  }
}
