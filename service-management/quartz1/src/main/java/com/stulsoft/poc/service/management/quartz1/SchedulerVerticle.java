/*
 * Created by Yuriy Stul 29 May 2018
 */
package com.stulsoft.poc.service.management.quartz1;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;

/**
 * Scheduler with Quartz
 * 
 * @author Yuriy Stul
 *
 */
public class SchedulerVerticle extends AbstractVerticle {
	private static final Logger logger = LoggerFactory.getLogger(SchedulerVerticle.class);

	private Scheduler scheduler;

	/* (non-Javadoc)
	 * @see io.vertx.core.AbstractVerticle#start()
	 */
	@Override
	public void start() throws Exception {
		logger.info("Starting SchedulerVerticle ...");
		super.start();

		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();

			// define the job and tie it to our ExecuteService1Job class
			JobDetail jobDetail = newJob(ExecuteService1Job.class)
					.withIdentity("service1Job", "serviceManagement")
					.build();
			jobDetail.getJobDataMap().put("vertx", vertx);

			Trigger trigger = newTrigger()
					.withIdentity("service1JobTrigger", "serviceManagement")
					.startNow()
					.withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?")) // Every 5 seconds
					.build();

			scheduler.scheduleJob(jobDetail, trigger);

			scheduler.start();
			logger.info("Scheduler started");

		} catch (SchedulerException ex) {
			logger.error("Failed create scheduler. {}", ex.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see io.vertx.core.AbstractVerticle#stop()
	 */
	@Override
	public void stop() throws Exception {
		logger.info("Stopping SchedulerVerticle");
		if (scheduler != null)
			scheduler.shutdown(true);
		super.stop();
	}

}
