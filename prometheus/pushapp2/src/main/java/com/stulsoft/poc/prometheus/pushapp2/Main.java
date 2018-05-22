/*
 * Created by Yuriy Stul 22 May 2018
 */
package com.stulsoft.poc.prometheus.pushapp2;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;

/**
 * @author Yuriy Stul
 *
 */
public class Main {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		logger.info("Started Main");
		JSONObject pushgatewayConf = null;
		JSONParser parser = new JSONParser();

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Main.class.getClassLoader().getResourceAsStream("config.json")))) {
			pushgatewayConf = (JSONObject) ((JSONObject) parser.parse(reader)).get("pushgateway");
		} catch (Exception e) {
			logger.error(e.getMessage());
			System.exit(1);
		}

		CollectorRegistry registry = new CollectorRegistry();
		PushGatewaySender sender = new PushGatewaySender(pushgatewayConf, registry, "pushapp2_job");
		Counter counter = Counter
				.build("wp_pushapp1_counter1", "The counter # 1")
				.register(registry);

		counter.inc();

		sender.pushMetrics();

		logger.info("Stopped Main");
	}
}
