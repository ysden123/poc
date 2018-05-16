/**
 * Created by Yuriy Stul 16 May 2018
 */
package com.stulsoft.poc.prometheus.pprometheus3;

import java.util.Objects;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.hotspot.BufferPoolsExports;
import io.prometheus.client.hotspot.ClassLoadingExports;
import io.prometheus.client.hotspot.GarbageCollectorExports;
import io.prometheus.client.hotspot.MemoryPoolsExports;
import io.prometheus.client.hotspot.StandardExports;
import io.prometheus.client.hotspot.ThreadExports;
import io.prometheus.client.hotspot.VersionInfoExports;

/**
 * @author Yuriy Stul
 *
 */
public class InitDefaultExports {
	private static boolean initialized = false;

	/**
	 * Register the default Hotspot collectors.
	 * 
	 * @param registry
	 *            collector registry
	 */
	public static synchronized void initialize(final CollectorRegistry registry) {
		Objects.requireNonNull(registry, "registry cann't be null");
		if (!initialized) {
			System.out.println("Initializing default exports...");
			new StandardExports().register(registry);
			new MemoryPoolsExports().register(registry);
			new BufferPoolsExports().register(registry);
			new GarbageCollectorExports().register(registry);
			new ThreadExports().register(registry);
			new ClassLoadingExports().register(registry);
			new VersionInfoExports().register(registry);
			initialized = true;
			System.out.println("Initialized default exports.");
		}
	}
}
