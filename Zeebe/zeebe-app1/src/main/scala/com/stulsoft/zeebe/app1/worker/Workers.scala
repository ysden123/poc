/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.zeebe.app1.worker

import com.stulsoft.zeebe.app1.AppConfig
import com.typesafe.scalalogging.LazyLogging
import io.zeebe.client.ZeebeClient

/** Workers
 *
 * @author Yuriy Stul
 */
object Workers extends App with LazyLogging {
  logger.info("Workers are started.")
  var zeebeClient: ZeebeClient = _
  try {
    zeebeClient = ZeebeClient.newClientBuilder()
      .brokerContactPoint(s"${AppConfig.zeebeHost}:${AppConfig.zeebePort}")
      .build()

    // register worker 'perform-payment'
    val jobType1 = "perform-payment"
    logger.info(s"Register $jobType1")
    zeebeClient.newWorker()
      .jobType(jobType1)
      .handler((jobClient, job) => {
        logger.info(s"$jobType1: Performing payment ...")
        logger.info(s"$jobType1: Data:")
        job.getVariablesAsMap.forEach((key, value) => logger.info(s"$jobType1: $key -> $value"))

        // add data
        val newData = new java.util.HashMap[String, Any]()
        newData.put("paymentId", 456)
        newData.put("sum", 11.07)
        newData.put("index", 789)
        newData.put("undeclared", "some test text") // Missing in BPMN as output parameter, will be invisible in the next tasks!
        jobClient.newCompleteCommand(job.getKey)
          .variables(newData)
          .send()
          .join()
      })
      .open()

    // register worker 'update-db'
    val jobType2 = "update-db"
    logger.info(s"Register $jobType2")
    zeebeClient.newWorker()
      .jobType(jobType2)
      .handler((jobClient, job) => {
        logger.info(s"$jobType2: Updating DB ...")
        logger.info(s"$jobType2: All data:")
        job.getVariablesAsMap.forEach((key, value) => logger.info(s"$jobType2: $key -> $value"))

        logger.info(s"$jobType2: Data:")
        val paymentId = job.getVariablesAsMap.get("paymentId")
        val sum = job.getVariablesAsMap.get("sum")
        logger.info(s"$jobType2: Input parameters: paymentId=$paymentId, sum=$sum")

        val newData = new java.util.HashMap[String, Any]()
        newData.put("result",s"done ${System.currentTimeMillis()}")
        jobClient.newCompleteCommand(job.getKey)
          .variables(newData)
          .send()
          .join()
      })
      .open()
  } catch {
    case ex: Exception =>
      logger.error(s"Failure: ${ex.getMessage}", ex)
      if (zeebeClient != null)
        zeebeClient.close()
  }

  sys.addShutdownHook({
    logger.info("Closing client...")
    if (zeebeClient != null)
      zeebeClient.close()
  })
}
