/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.zeebe.app1.metadatamngmt


import com.stulsoft.zeebe.app1.AppConfig
import com.typesafe.scalalogging.LazyLogging
import io.zeebe.client.ZeebeClient
import scala.jdk.CollectionConverters._

/** Metadata manager
 * @author Yuriy Stul
 */
object MetadataManager extends App with LazyLogging {
  logger.info("Metadata manager is started.")
  var zeebeClient: ZeebeClient = _
  try {
    zeebeClient = ZeebeClient.newClientBuilder()
      .brokerContactPoint(s"${AppConfig.zeebeHost}:${AppConfig.zeebePort}")
      .build()

    // deploy workflow
    val workflowDefPath = "src/main/resources/client-service.bpmn"
    logger.info(s"Deploying $workflowDefPath ...")
    val deployment = zeebeClient.newDeployCommand()
      .addResourceFile(workflowDefPath)
      .send()
      .join()

    deployment.getWorkflows.asScala.foreach(f => logger.info(s"BpmnProcessId=${f.getBpmnProcessId}, ResourceName=${f.getResourceName}, Version=${f.getVersion}, WorkflowKey=${f.getWorkflowKey}"))

    val version = deployment.getWorkflows.get(0).getVersion
    logger.info(s"Deployed version=$version")
  } catch {
    case ex: Exception =>
      logger.error(s"Failure: ${ex.getMessage}", ex)
  } finally {
    if (zeebeClient != null)
      zeebeClient.close()
  }
}
