/*
 * Created by Yuriy Stul 27 May 2018
 */
package com.stulsoft.poc.service.management.chime1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;

/**
 * Executes some service every 1 second 3 times
 * 
 * @author Yuriy Stul
 *
 */
public class Test1 {
	private static final Logger logger = LoggerFactory.getLogger(Test1.class);

	// Scheduling with Chime
	static void scheduling(Vertx vertx) {
		logger.info("==>scheduling");

		EventBus eventBus = vertx.eventBus();
		// Consumer of the timer events
		MessageConsumer<JsonObject> consumer = eventBus.consumer("scheduler:timer");
		// Listens and prints timer events. When timer completes stops the Vertx
		consumer.handler(
				message -> {
					JsonObject event = message.body();
					if (event.getString("event").equals("complete")) {
						logger.info("completed");
						vertx.close();
					} else {
						logger.info(event.toString());
					}
				});
		logger.info("Create new timer");
		// Create new timer
		eventBus.send(
				"chime",
				(new JsonObject()).put("operation", "create").put("name", "scheduler:timer")
						.put("publish", false).put("max count", 3)
						.put("description", (new JsonObject()).put("type", "interval").put("delay", 1)),
				ar -> {
					if (ar.succeeded()) {
						logger.info("Scheduling started: " + ar.result().body());
					} else {
						logger.info("Message failed: " + ar.cause());
						vertx.close();
					}
				});
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		logger.info("==>main");
		try {
			// Vertx vertx = Vertx.vertx();

			VertxOptions vertxOptions = new VertxOptions();
			vertxOptions.setBlockedThreadCheckInterval(1000000);
			Vertx vertx = Vertx.vertx(vertxOptions);
			// deploying Chime
			logger.info("deploying Chime");
			vertx.deployVerticle("ceylon:herd.schedule.chime/0.2.1", res -> {
				if (res.succeeded()) {
					logger.info("Deployed Chime");
					// Chime has been successfully deployed - start scheduling
					scheduling(vertx);
				} else {
					logger.info("Deployment failed! " + res.cause());
					vertx.close();
				}
			});
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		logger.info("<==main");
	}

}
