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
public class Service4 extends AbstractVerticle {
	private static final Logger logger=LoggerFactory.getLogger(Service4.class);
	
	public static final String ADDRESS = "service4";

	/* (non-Javadoc)
	 * @see io.vertx.core.AbstractVerticle#start()
	 */
	@Override
	public void start() throws Exception {
		super.start();
		logger.info("Starting Service4...");
		vertx.eventBus().consumer(ADDRESS, this::handler);
	}

	/* (non-Javadoc)
	 * @see io.vertx.core.AbstractVerticle#stop()
	 */
	@Override
	public void stop() throws Exception {
		super.stop();
		logger.info("Stopping Service4...");
	}

	private void handler(Message<String> message) {
		logger.info("Received message {}", message.body());
		vertx.executeBlocking((f)->{
			try {
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
	}
}
