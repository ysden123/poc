/**
 * Created by Yuriy Stul 17 May 2018
 */
package com.stulsoft.poc.prometheus.manager.samples.server1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;

/**
 * Test service
 * 
 * @author Yuriy Stul
 *
 */
public class Service1 extends AbstractVerticle {
	public static final String EB_SERVICE_ADDRESS = "serviceAddress";
	private static Logger logger = LoggerFactory.getLogger(Service1.class);

	private void handleMessage(Message<String> message) {
		message.reply("Reply from Service1");
	}

	/* (non-Javadoc)
	 * @see io.vertx.core.AbstractVerticle#start()
	 */
	@Override
	public void start() throws Exception {
		super.start();
		logger.info("Starting Service...");
		vertx.eventBus().consumer(EB_SERVICE_ADDRESS, this::handleMessage);
	}

	/* (non-Javadoc)
	 * @see io.vertx.core.AbstractVerticle#stop()
	 */
	@Override
	public void stop() throws Exception {
		super.stop();
		logger.info("Stopping Service...");
	}
}
