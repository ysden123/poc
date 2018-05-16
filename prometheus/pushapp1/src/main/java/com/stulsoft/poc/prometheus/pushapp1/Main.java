/**
 * Created by Yuriy Stul 16 May 2018
 */
package com.stulsoft.poc.prometheus.pushapp1;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.PushGateway;

/**
 * @author Yuriy Stul
 *
 */
public class Main {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		logger.info("Started Main");

		Random random = new Random(System.currentTimeMillis());

		CollectorRegistry registry = new CollectorRegistry();
		Counter counter = Counter
				.build("wp_pushapp1_counter1", "The counter # 1")
				.register(registry);
		Gauge gauge = Gauge
				.build("wp_pushapp1_gauge1", "The gauge # 1")
				.register(registry);
		Gauge duration = Gauge
				.build("wp_pushapp1_duration", "Duration of pushapp1 service")
				.register(registry);
		Gauge lastSuccess = Gauge
				.build("wp_pushapp1_last_success", "Last time of success for pushapp1 service")
				.register(registry);
		Gauge runningStatus = Gauge
				.build("wp_pushapp1_running", "Running status for pushapp1 service")
				.register(registry);
		Gauge executionTime = Gauge
				.build("wp_pushapp1_execution_time", "Execution time for pushapp1 service")
				.register(registry);

		logger.info("Set status to 1.0");
		runningStatus.set(1.0);
		PushGateway pushGateway = new PushGateway("localhost:9091");
		try {
			pushGateway.pushAdd(registry, "pushapp1_job");
			logger.info("Pushed metrics");
		} catch (IOException e) {
			logger.error("Failed during push. {}", e.getMessage());
		}

		executionTime.set(Utils.timeToMinutes(new Date(System.currentTimeMillis())));
		logger.info("executionTime = {}", executionTime.get());
		counter.inc();
		gauge.set(random.nextDouble());
		Gauge.Timer durationTimer = duration.startTimer();
		try {
			Thread.sleep((long) (20000 * random.nextDouble()));
			lastSuccess.setToCurrentTime();
			logger.info("lastSuccess = {}", lastSuccess.get());
		} catch (InterruptedException ignore) {
		} finally {
			durationTimer.setDuration();
		}

		try {
			Thread.sleep(15000);
			logger.info("Set status to 0.0");
			runningStatus.set(0.0);
		} catch (Exception ignore) {
		}

		try {
			pushGateway.pushAdd(registry, "pushapp1_job");
			logger.info("Pushed metrics");
		} catch (IOException e) {
			logger.error("Failed during push. {}", e.getMessage());
		}
		logger.info("Stopped Main");
	}
}
