/*
 * Created by Yuriy Stul 28 May 2018
 */
package com.stulsoft.poc.service.management.quartz1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;

/**
 * @author Yuriy Stul
 *
 */
public class Service1 extends AbstractVerticle {
	private static final Logger logger = LoggerFactory.getLogger(Service1.class);

	public static final String EB_ADDRESS = "service1";

	/* (non-Javadoc)
	 * @see io.vertx.core.AbstractVerticle#start()
	 */
	@Override
	public void start() throws Exception {
		super.start();
		logger.info("Starting Service1");
		vertx.eventBus().consumer(EB_ADDRESS, this::executeLongTimeJob);
	}

	/* (non-Javadoc)
	 * @see io.vertx.core.AbstractVerticle#stop()
	 */
	@Override
	public void stop() throws Exception {
		super.stop();
		logger.info("Stopping Service1");
	}

	private void executeLongTimeJob(Message<String> message) {
		logger.info("==>executeLongTimeJob");

//		vertx.setTimer(1000 * 60 * 3, l->{
		vertx.setTimer(500, l->{
			logger.info("Completed execution");
			message.reply("Service1 completed execution");
		});
	}

}
