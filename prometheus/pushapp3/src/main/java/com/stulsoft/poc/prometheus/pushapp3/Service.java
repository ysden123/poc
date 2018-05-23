/*
 * Created by Yuriy Stul 23 May 2018
 */
package com.stulsoft.poc.prometheus.pushapp3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.prometheus.client.Counter;
import io.vertx.core.AbstractVerticle;

/**
 * @author Yuriy Stul
 *
 */
public class Service extends AbstractVerticle {
	private static final Logger logger = LoggerFactory.getLogger(Service.class);
	private final Counter counter;
	private final PushGatewaySender sender;

	public Service(final Counter counter, final PushGatewaySender sender) {
		this.counter = counter;
		this.sender = sender;
	}

	@Override
	public void start() throws Exception {
		super.start();
		logger.info("Starting Service...");
		vertx.setTimer(1000, l -> {
			counter.inc();
			try {
				sender.pushMetrics();
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			logger.info("Completed service");
			vertx.close();
		});
	}

	@Override
	public void stop() throws Exception {
		logger.info("Stoping Service...");
		super.stop();
	}

}
