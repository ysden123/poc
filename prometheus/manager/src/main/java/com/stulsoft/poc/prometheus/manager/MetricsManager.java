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
import io.prometheus.client.hotspot.BufferPoolsExports;
import io.prometheus.client.hotspot.ClassLoadingExports;
import io.prometheus.client.hotspot.GarbageCollectorExports;
import io.prometheus.client.hotspot.MemoryPoolsExports;
import io.prometheus.client.hotspot.StandardExports;
import io.prometheus.client.hotspot.ThreadExports;
import io.prometheus.client.hotspot.VersionInfoExports;

/**
 * Prometheus metrics manager.
 * 
 * @author Yuriy Stul
 *
 */
public class MetricsManager {
	private static final Logger logger = LoggerFactory.getLogger(MetricsManager.class);
	private static final Object lock = new Object();
	private static boolean defaultMetricsInitialized = false;
	private static MetricsManager instance = null;

	public static final String METRICS_NAME_PREFIX = "webpals";

	private final CollectorRegistry registry;
	private final HashMap<String, Counter> counters;
	private final HashMap<String, Gauge> gauges;
	private final MetricsHandler metricsHandler;

	/**
	 * Initializes a new instance of the MetricsManager class.
	 */
	private MetricsManager() {
		registry = new CollectorRegistry();
		counters = new HashMap<>();
		gauges = new HashMap<>();
		metricsHandler = new MetricsHandler(registry);
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
		return Counter.sanitizeMetricName(String.format("%s_%s_%s", METRICS_NAME_PREFIX, serviceName, metricsName));
	}

	/**
	 * Initializes a default JVM metrics.
	 */
	public void initDefaultMetrics() {
		if (!defaultMetricsInitialized) {
			synchronized (lock) {
				if (!defaultMetricsInitialized) {
					logger.info("Initializing default exports...");
					new StandardExports().register(registry);
					new MemoryPoolsExports().register(registry);
					new BufferPoolsExports().register(registry);
					new GarbageCollectorExports().register(registry);
					new ThreadExports().register(registry);
					new ClassLoadingExports().register(registry);
					new VersionInfoExports().register(registry);
					defaultMetricsInitialized = true;
					logger.info("Initialized default exports.");
				}
			}
		}
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

	public MetricsHandler getMetricsHandler() {
		return metricsHandler;
	}

	/**
	 * Adds a new Counter.
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

	/**
	 * Returns a Counter for specified service and counter names.
	 * 
	 * @param serviceName
	 *            specifies service
	 * @param counterName
	 *            specifies counter
	 * @return the Counter for specified service and counter names.
	 * @throws RuntimeException
	 *             if no counter exists
	 */
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

	/**
	 * Adds a new Gauge.
	 * 
	 * <p>
	 * If a gauge with same service and counter names already exists then the method
	 * returns existing gauge. Otherwise the method creates new gauge and returns
	 * created gauge.
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
	 * @return the Gauge
	 */
	public Gauge addGauge(final String serviceName, final String counterName, final String description,
			final String... labelNames) {
		Objects.requireNonNull(serviceName, "serviceName should be defined");
		Objects.requireNonNull(counterName, "counterName should be defined");
		Objects.requireNonNull(description, "description should be defined");
		final String fullMetricsName = buildMetricsName(serviceName, counterName);
		Gauge gauge = gauges.get(fullMetricsName);
		if (gauge == null) {
			synchronized (lock) {
				if (gauge == null) {
					Gauge.Builder builder = Gauge
							.build(fullMetricsName, description);
					if (labelNames != null && labelNames.length > 0) {
						builder.labelNames(labelNames);
					}

					gauge = builder.register(registry);
					gauges.put(fullMetricsName, gauge);
				}
			}
		}
		return gauge;
	}

	/**
	 * Returns a Gauge for specified service and counter names.
	 * 
	 * @param serviceName
	 *            specifies service
	 * @param gaugeName
	 *            specifies gauge
	 * @return the Gauge for specified service and gauge names.
	 * @throws RuntimeException
	 *             if no gauge exists
	 */
	public Gauge getGauge(final String serviceName, final String gaugeName) {
		final String fullMetricsName = buildMetricsName(serviceName, gaugeName);
		Gauge gauge = gauges.get(fullMetricsName);
		if (gauge == null) {
			final String message = String.format("Gauge with service name %s and gauge name %s doesn't exist.",
					serviceName, gaugeName);
			logger.error(message);
			throw new RuntimeException(message);
		}
		return gauge;
	}

}
