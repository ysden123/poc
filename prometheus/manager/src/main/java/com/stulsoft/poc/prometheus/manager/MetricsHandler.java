/*
 * Created by Yuriy Stul 17 May 2018
 */
package com.stulsoft.poc.prometheus.manager;

import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.common.TextFormat;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;

/**
 * Metrics Handler for Vert.x Web.
 * <p>
 * This handler will allow the usage of Prometheus Client Java API with Vert.x
 * applications and expose a API compatible handler for the collector.
 * <p>
 * Usage:
 * <p>
 * router.route("/metrics").handler(new MetricsHandler());
 * 
 * @author Yuriy Stul
 *
 */
public class MetricsHandler implements Handler<RoutingContext> {
	private static Logger logger = LoggerFactory.getLogger(MetricsHandler.class);

	/**
	 * Wrap a Vert.x Buffer as a Writer so it can be used with TextFormat writer
	 */
	private static class BufferWriter extends Writer {

		private final Buffer buffer = Buffer.buffer();

		@Override
		public void write(char[] cbuf, int off, int len){
			buffer.appendString(new String(cbuf, off, len));
		}

		@Override
		public void flush(){
			// NO-OP
		}

		@Override
		public void close(){
			// NO-OP
		}

		Buffer getBuffer() {
			return buffer;
		}
	}

	private CollectorRegistry registry;

	/**
	 * Construct a MetricsHandler for the given registry.
	 * 
	 * @param registry
	 *            the collector registry
	 */
	public MetricsHandler(CollectorRegistry registry) {
		this.registry = registry;
	}

	private Set<String> parse(HttpServerRequest request) {
		return new HashSet<>(request.params().getAll("name[]"));
	}

	public void handle(RoutingContext ctx) {
		try {
			final BufferWriter writer = new BufferWriter();
			TextFormat.write004(writer, registry.filteredMetricFamilySamples(parse(ctx.request())));
			ctx.response().setStatusCode(200).putHeader("Content-Type", TextFormat.CONTENT_TYPE_004)
					.end(writer.getBuffer());
		} catch (IOException e) {
			logger.error("Failed sending metrics. {}", e.getMessage());
			ctx.fail(e);
		}
	}
}
