/*
 * Created by Yuriy Stul 29 May 2018
 */
package com.stulsoft.poc.service.management.quartz1;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Vertx;

/**
 * @author Yuriy Stul
 *
 */
@DisallowConcurrentExecution
public class ExecuteService1Job implements Job {
	private static final Logger logger = LoggerFactory.getLogger(ExecuteService1Job.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("Send message to execute Service1");
		final Vertx vertx = (Vertx) context.getJobDetail().getJobDataMap().get("vertx");
		vertx.eventBus().publish(Service1.EB_ADDRESS, "execute");
	}
}
