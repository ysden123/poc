/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.zeebe.msg1.wfrunner

import com.stulsoft.zeebe.msg1.AppConfig
import com.typesafe.scalalogging.LazyLogging
import io.zeebe.client.ZeebeClient

/**
 * @author Yuriy Stul
 */
object WFRunner extends App with LazyLogging {
  logger.info("WFRunner is started.")
  var zeebeClient: ZeebeClient = _
  try {
    zeebeClient = ZeebeClient.newClientBuilder()
      .brokerContactPoint(s"${AppConfig.zeebeHost}:${AppConfig.zeebePort}")
      .build()

    // create new instance of the workflow
    logger.info("Creating new instance of the workflow")
    val data = new java.util.HashMap[String, Any]()
    data.put("orderId", 123)

    val wfInstance = zeebeClient.newCreateInstanceCommand()
      .bpmnProcessId("DemoMessageWF_ID")
      .latestVersion()
      .variables(data)
      .send()
      .join()
    val workflowInstanceKey = wfInstance.getWorkflowInstanceKey
    logger.info(s"Workflow instance created. Key: $workflowInstanceKey")

  } catch {
    case ex: Exception =>
      logger.error(s"Failure: ${ex.getMessage}", ex)
  } finally {
    if (zeebeClient != null)
      zeebeClient.close()
  }
}

