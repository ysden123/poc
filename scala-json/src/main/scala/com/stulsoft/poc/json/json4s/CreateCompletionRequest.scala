/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.poc.json.json4s

import org.json4s.{DefaultFormats, Formats}
import org.json4s.jackson.Serialization.write

case class CreateCompletionRequest(model: String,
                                   prompt: String,
                                   suffix: String,
                                   max_tokens: Int,
                                   temperature: Double,
                                   top_p: Double,
                                   n: Int,
                                   stream: Boolean,
                                   logprobs: Int,
                                   echo: Boolean,
                                   stop: String,
                                   presence_penalty: Double,
                                   frequency_penalty: Double,
                                   best_of: Int,
                                   user: String
                                  )

object CreateCompletionRequest:
  given formats: DefaultFormats = DefaultFormats

  def toJson(createCompletionRequest: CreateCompletionRequest): String =
    write(createCompletionRequest)

  def main(args: Array[String]): Unit = {
    println("==>main")
    //    val request = CreateCompletionRequest(model = "test", prompt = "test prom", max_tokens = 7, temperature = 0, top_p = 1, n = 1, stop = Option("\n"))
    val request = CreateCompletionRequest("model",
      "prompt",
      "suffix",
      0,      // max_tokens
      1.0,    // temperature
      1.0,    // top_p
      2,      // n
      false,  // stream
      1,      // logprobs
      false,  // echo
      "stop",
      3.0,    // presence_penalty
      1,      // frequency_penalty
      1,      // best_of
      "user")
    println(request)
    println(s"JSON: ${CreateCompletionRequest.toJson(request)}")
  }

