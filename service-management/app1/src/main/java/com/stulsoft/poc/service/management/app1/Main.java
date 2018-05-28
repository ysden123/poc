/*
 * Created by Yuriy Stul 28 May 2018
 */
package com.stulsoft.poc.service.management.app1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;

/**
 * @author Yuriy Stul
 *
 */
public class Main {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

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
						vertx.eventBus().publish(Service1.EB_ADDRESS, "execute");
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
						.put("description", (new JsonObject())
								.put("type", "cron")
								.put("seconds", "0")
								.put("minutes", "0/5")
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
		logger.info("Started app1");
		try {
			VertxOptions vertxOptions = new VertxOptions()
					.setBlockedThreadCheckInterval(1000000);
			Vertx vertx = Vertx.vertx(vertxOptions);

			Verticle servive1 = new Service1();

			// deploying Service5
			logger.info("deploying Service1");
			DeploymentOptions deploymentOptions = new DeploymentOptions()
					.setWorker(true)
					.setMaxWorkerExecuteTime(1000 * 1000 * 60 * 4);
			vertx.deployVerticle(servive1, deploymentOptions);

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
	}
}
