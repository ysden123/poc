/*
 * Created by Yuriy Stul 22 May 2018
 */
package com.stulsoft.poc.prometheus.pushapp2;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.PushGateway;

/**
 * @author Yuriy Stul
 *
 */
public class PushGatewaySender {
	private static Logger logger = LoggerFactory.getLogger(PushGatewaySender.class);
	private final PushGateway pushGateway;
	private final CollectorRegistry registry;
	private final String jobName;

	public PushGatewaySender(final JSONObject conf, final CollectorRegistry registry, final String jobName) {
		pushGateway = new PushGateway(String.format("%s:%s", (String) conf.get("host"), (String) conf.get("port")));
		this.registry = registry;
		this.jobName = jobName;
	}

	public void pushMetrics() {
		try {
			pushGateway.pushAdd(registry, jobName);
		} catch (IOException e) {
			String msg = "Failed push metrics. " + e.getMessage();
			logger.error(msg);
			throw new RuntimeException(msg);
		}
	}
}
