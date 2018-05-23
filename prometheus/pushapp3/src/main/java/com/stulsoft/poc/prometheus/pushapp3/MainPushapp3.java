/*
 * Created by Yuriy Stul 23 May 2018
 */
package com.stulsoft.poc.prometheus.pushapp3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

/**
 * @author Yuriy Stul
 *
 */
public class MainPushapp3 {
private static final Logger logger = LoggerFactory.getLogger(MainPushapp3.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		logger.info("Started MainPushapp3");
		Vertx vertx = Vertx.vertx();
		

		ConfigStoreOptions fileStore = new ConfigStoreOptions()
                .setType("file")
                .setConfig(new JsonObject().put("path", "config.json"));
		
		ConfigRetrieverOptions options = new ConfigRetrieverOptions()
                .addStore(fileStore);
        ConfigRetriever retriever = ConfigRetriever.create(vertx, options);
        retriever.getConfig(ar -> {
            if (ar.failed()) {
                logger.error("Failed getting configuration. {}", ar.cause().getMessage());
            } else {
                DeploymentOptions deploymentOptions = new DeploymentOptions()
                        .setConfig(ar.result());
                CollectorRegistry registry = new CollectorRegistry();
        		Counter counter = Counter
        				.build("wp_pushapp3_counter1", "The counter # 1")
        				.register(registry);
        		PushGatewaySender sender = new PushGatewaySender(ar.result().getJsonObject("pushgateway"), registry, "pushapp3_job");
        		Verticle service = new Service(counter, sender);
                vertx.deployVerticle(service, deploymentOptions);
            }
        });
	}
}
