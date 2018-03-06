/*
   Created by Yuriy Stul 2018
*/
package com.stulsoft.pspring.scala

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.{CommandLineRunner, SpringApplication}

@SpringBootApplication
class Main1 extends CommandLineRunner {
  override def run(strings: String*): Unit = {
    println(s"==>Main1.run")

  }
}

object Main1 {
  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[Main1], args: _*)
  }
}
