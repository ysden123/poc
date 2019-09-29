/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.zeebe.msg1.worker

import com.stulsoft.zeebe.msg1.AppConfig
import com.typesafe.scalalogging.LazyLogging
import io.zeebe.client.ZeebeClient
import io.zeebe.client.api.response.ActivatedJob

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

    // register worker 'init-payment'
    val jobType1 = "init-payment"
    logger.info(s"Register $jobType1")
    zeebeClient.newWorker()
      .jobType(jobType1)
      .handler((jobClient, job) => {
        logger.info(s"$jobType1: Initiating payment ...")
        outputData(jobType1, job)
        //
        //        // add data
        //        val newData = new java.util.HashMap[String, Any]()
        //        newData.put("paymentId", 456)
        //        newData.put("sum", 11.07)
        //        newData.put("index", 789)
        //        newData.put("undeclared", "some test text") // Missing in BPMN as output parameter, will be invisible in the next tasks!
        //        jobClient.newCompleteCommand(job.getKey)
        //          .variables(newData)
        //          .send()
        //          .join()
        //      })
        //      .open()
        jobClient.newCompleteCommand(job.getKey)
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
        outputData(jobType2, job)
        jobClient.newCompleteCommand(job.getKey)
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

  private def outputData(jobType: String, job: ActivatedJob): Unit = {
    logger.info(s"$jobType -> data: ")
    job.getVariablesAsMap.forEach((key, value) => logger.info(s"$jobType: $key -> $value"))
  }
}
