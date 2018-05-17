/**
 * Created by Yuriy Stul 17 May 2018
 */
package com.stulsoft.poc.prometheus.manager;

import java.util.HashMap;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;

/**
 * Prometheus metrics manager.
 * 
 * @author Yuriy Stul
 *
 */
public class MetricsManager {
	private static final Logger logger = LoggerFactory.getLogger(MetricsManager.class);
	private static final Object lock = new Object();
	private static MetricsManager instance = null;

	private static final String METRICS_NAME_PREFIX = "webpals";

	private final CollectorRegistry registry = new CollectorRegistry();
	private final HashMap<String, Counter> counters;
	private final HashMap<String, Gauge> gauges;

	/**
	 * Initializes a new instance of the MetricsManager class.
	 */
	private MetricsManager() {
		counters = new HashMap<>();
		gauges = new HashMap<>();
	}

	/**
	 * Returns full metrics name in the format PREFIX_SERVICENAME_METRICSNAME
	 * 
	 * @param serviceName
	 *            the service name
	 * @param metricsName
	 *            the metrics name
	 * @return full metrics name in the format PREFIX_SERVICENAME_METRICSNAME
	 */
	private String buildMetricsName(final String serviceName, final String metricsName) {
		return String.format("%s_%s_%s", METRICS_NAME_PREFIX, serviceName, metricsName);
	}

	/**
	 * Returns an instance of the MetricsManager class.
	 * 
	 * @return the instance of the MetricsManager class.
	 */
	public static MetricsManager getInstance() {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = new MetricsManager();
				}
			}
		}
		return instance;
	}

	/**
	 * Adds a new Counter without labels.
	 * 
	 * <p>
	 * If a counter with same service and counter names already exists then the
	 * method returns existing counter. Otherwise the method creates new counter and
	 * returns created counter.
	 * </p>
	 * 
	 * @param serviceName
	 *            specifies the service name
	 * @param counterName
	 *            specifies the counter name
	 * @param description
	 *            specifies the description
	 * @param labelNames
	 *            optional, specifies labels
	 * @return the Counter
	 */
	public Counter addCounter(final String serviceName, final String counterName, final String description,
			final String... labelNames) {
		Objects.requireNonNull(serviceName, "serviceName should be defined");
		Objects.requireNonNull(counterName, "counterName should be defined");
		Objects.requireNonNull(description, "description should be defined");
		final String fullMetricsName = buildMetricsName(serviceName, counterName);
		Counter counter = counters.get(fullMetricsName);
		if (counter == null) {
			synchronized (lock) {
				if (counter == null) {
					Counter.Builder builder = Counter
							.build(fullMetricsName, description);
					if (labelNames != null && labelNames.length > 0) {
						builder.labelNames(labelNames);
					}
					counter = builder.register(registry);
					counters.put(fullMetricsName, counter);
				}
			}
		}
		return counter;
	}

	public Counter getCounter(final String serviceName, final String counterName) {
		final String fullMetricsName = buildMetricsName(serviceName, counterName);
		Counter counter = counters.get(fullMetricsName);
		if (counter == null) {
			final String message = String.format("Counter with service name %s and counter name %s doesn't exist.",
					serviceName, counterName);
			logger.error(message);
			throw new RuntimeException(message);
		}
		return counter;
	}
}
