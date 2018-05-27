/*
 * Created by Yuriy Stul 27 May 2018
 */
package com.stulsoft.poc.service.management.chime1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;

/**
 * Executes some service each 5 seconds with failure on service5
 * 
 * @author Yuriy Stul
 *
 */
public class Test5 {
	private static final Logger logger = LoggerFactory.getLogger(Test5.class);

	private static void scheduling(final Vertx vertx) {
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
						logger.info("Sending start message to Service5");
						vertx.eventBus().send(Service5.ADDRESS, "execute", ar->{
							if (ar.succeeded())
								logger.info("+++Reply from Service5: {}", ar.result().body());
							else
								logger.error("***Failed Service5: {}", ar.cause().getMessage());
						});
					}
				});

		// Create new timer
		logger.info("Create new timer");
		eventBus.send(
				"chime",
				(new JsonObject())
						.put("operation", "create")
						.put("name", "scheduler:timer")
						.put("publish", false)
						.put("max count", 3)
						.put("description", (new JsonObject())
								.put("type", "cron")
						.put("seconds", "1/5")
						.put("minutes", "*")
						.put("hours", "*")
						.put("days of month", "*")
						.put("months", "*")),
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
			VertxOptions vertxOptions = new VertxOptions();
			vertxOptions.setBlockedThreadCheckInterval(1000000);
			Vertx vertx = Vertx.vertx(vertxOptions);
			
			Verticle servive4 = new Service5();
			// deploying Service5
			logger.info("deploying Service5");
			vertx.deployVerticle(servive4);
			
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
