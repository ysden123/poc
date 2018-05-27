/*
 * Created by Yuriy Stul 27 May 2018
 */
package com.stulsoft.poc.service.management.chime1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;

/**
 * @author Yuriy Stul
 *
 */
public class Service5 extends AbstractVerticle {
	private static final Logger logger=LoggerFactory.getLogger(Service5.class);
	
	public static final String ADDRESS = "service5";
	
	private int status;

	/* (non-Javadoc)
	 * @see io.vertx.core.AbstractVerticle#start()
	 */
	@Override
	public void start() throws Exception {
		super.start();
		logger.info("Starting Service4...");
		status = 0;
		vertx.eventBus().consumer(ADDRESS, this::handler);
	}

	/* (non-Javadoc)
	 * @see io.vertx.core.AbstractVerticle#stop()
	 */
	@Override
	public void stop() throws Exception {
		super.stop();
		logger.info("Stopping Service5...");
	}

	private void handler(Message<String> message) {
		logger.info("Received message {}", message.body());
		if (status == 0) {
		vertx.executeBlocking((f)->{
			try {
				status = 1;
				Thread.sleep(5500);
				logger.info("Completed running");
				message.reply("Completed running");
			} catch (InterruptedException ignore) {
			}
		}, ar->{
			if (ar.succeeded()) {
				logger.info("Done");
				message.reply("Done");
			}else {
				logger.error(ar.cause().getMessage());
			}
		});
		}else {
			logger.warn("Status is {}", status);
			message.fail(123, "Not completed previous execution");
		}
	}
}
