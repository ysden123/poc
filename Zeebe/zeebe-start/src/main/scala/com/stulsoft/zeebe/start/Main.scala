/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.zeebe.start

import io.zeebe.client.ZeebeClient

/**
 * See <a href="https://docs.zeebe.io/java-client/get-started.html">Get started</a>
 *
 * @author Yuriy Stul
 */
object Main extends App {
  try {
    val zeebeClient = ZeebeClient.newClientBuilder()
      .brokerContactPoint(s"${AppConfig.zeebeHost}:${AppConfig.zeebePort}")
      .build()

    println("Connected.")

    // after the client connected
    // deploy workflow
    val deployment = zeebeClient.newDeployCommand()
      .addResourceFile("src/main/resources/client-service.bpmn")
      .send()
      .join()

    val version = deployment.getWorkflows.get(0).getVersion
    println(s"Deployed version=$version")

    // after the workflow is deployed
    // create new instance of the workflow
    val data = new java.util.HashMap[String,Any]()
    data.put("paymentId", 123)
    data.put("sum", 10.55)
    val wfInstance = zeebeClient.newCreateInstanceCommand()
      .bpmnProcessId("ClientService_ID")
      .latestVersion()
      .variables(data)
      .send()
      .join()
    val workflowInstanceKey = wfInstance.getWorkflowInstanceKey
    println(s"Workflow instance created. Key: $workflowInstanceKey")

    // after the workflow instance is created
    // register worker 'perform-payment'
    zeebeClient.newWorker()
      .jobType("perform-payment")
      .handler((jobClient, job) => {
        println("Performing payment ...")
        println("Data:")
        job.getVariablesAsMap.forEach((key,value)=>println(s"$key -> $value"))

        // add data
        val newData = new java.util.HashMap[String,Any]()
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
    zeebeClient.newWorker()
      .jobType("update-db")
      .handler((jobClient, job) => {
        println("Updating DB ...")
        println("All data:")
        job.getVariablesAsMap.forEach((key,value)=>println(s"$key -> $value"))

        println("Data:")
        val paymentId = job.getVariablesAsMap.get("paymentId")
        val sum = job.getVariablesAsMap.get("sum")
        println(s"Input parameters: paymentId=$paymentId, sum=$sum")
        jobClient.newCompleteCommand(job.getKey)
          .send()
          .join()
      })
      .open()

    // waiting for the jobs

    // Don't close, we need to keep polling to get work
    //     jobWorker.close()

//    zeebeClient.close()
//    println("Closed.")
  } catch {
    case ex: Exception =>
      println(s"Error: ${ex.getMessage}")
  }
}
