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
 * Executes some service each 5 seconds. Many timers.
 * 
 * <p>
 * Works with Service4 and Service41
 * 
 * @author Yuriy Stul
 *
 */
public class Test6 {
	private static final Logger logger = LoggerFactory.getLogger(Test6.class);

	private static void scheduling(final Vertx vertx) {
		logger.info("==>scheduling");
		EventBus eventBus = vertx.eventBus();

		// Consumer of the timer events
		MessageConsumer<JsonObject> consumer = eventBus.consumer("scheduler:timer1");
		// Listens and prints timer events. When timer completes stops the Vertx
		consumer.handler(
				message -> {
					JsonObject event = message.body();
					logger.info("Event entities:");
					event.forEach(e->{
						logger.info("Event entity: {} -> {}", e.getKey(),e.getValue());
					});
					if (event.getString("event").equals("complete")) {
						logger.info("completed");
						vertx.close();
					} else {
						logger.info(event.toString());
						logger.info("Sending start message to Service4");
						vertx.eventBus().send(Service4.ADDRESS, "execute", ar->{
							if (ar.succeeded())
								logger.info("Reply from Service4: {}", ar.result().body());
							else
								logger.error("Failed Service4: {}", ar.cause().getMessage());
						});
					}
				});

		// Consumer of the timer events
		MessageConsumer<JsonObject> consumer2 = eventBus.consumer("scheduler:timer2");
		// Listens and prints timer events. When timer completes stops the Vertx
		consumer2.handler(
				message -> {
					JsonObject event = message.body();
					logger.info("Event entities:");
					event.forEach(e->{
						logger.info("Event entity: {} -> {}", e.getKey(),e.getValue());
					});
					if (event.getString("event").equals("complete")) {
						logger.info("completed");
						vertx.close();
					} else {
						logger.info(event.toString());
						logger.info("Sending start message to Service41");
						vertx.eventBus().send(Service41.ADDRESS, "execute", ar->{
							if (ar.succeeded())
								logger.info("Reply from Service41: {}", ar.result().body());
							else
								logger.error("Failed Service41: {}", ar.cause().getMessage());
						});
					}
				});

		// Create new timers
		logger.info("Create new timer");
		eventBus.send(
				"chime",
				(new JsonObject())
						.put("operation", "create")
						.put("name", "scheduler:timer1")
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
		
		eventBus.send(
				"chime",
				(new JsonObject())
						.put("operation", "create")
						.put("name", "scheduler:timer2")
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
			
			Verticle servive4 = new Service4();
			// deploying Service4
			logger.info("deploying Service4");
			vertx.deployVerticle(servive4);
			
			Verticle servive41 = new Service41();
			// deploying Service41
			logger.info("deploying Service41");
			vertx.deployVerticle(servive41);
			
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
