/**
 * Created by Yuriy Stul 17 May 2018
 */
package com.stulsoft.poc.prometheus.manager.samples.server1;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stulsoft.poc.prometheus.manager.MetricsManager;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.ReplyException;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/**
 * Test HttpServer
 * 
 * @author Yuriy Stul
 *
 */
public class HttpServer extends AbstractVerticle {
	private static Logger logger = LoggerFactory.getLogger(HttpServer.class);

	private static final String SERVICE_NAME = "service_sample1";
	private static final String COUNTER_NAME_GETS = "gets";

	@Override
	public void start(Future<Void> startFuture) {
		logger.info("Starting HTTP server...");
		MetricsManager.getInstance().addCounter(SERVICE_NAME, COUNTER_NAME_GETS, "Counts all gets");
		io.vertx.core.http.HttpServer server = vertx.createHttpServer();

		Router router = Router.router(vertx);
		router.get("/").handler(this::serviceHandler);
		router.get("/metrics").handler(MetricsManager.getInstance().getMetricsHandler());

		final int port = 8080;

		server.requestHandler(router::accept).listen(port, ar -> {
			if (ar.succeeded()) {
				logger.info("HTTP server running on port {}", port);
				startFuture.complete();
			} else {
				logger.error("Failed start HTTP server. {}", ar.cause());
				startFuture.fail(ar.cause());
			}
		});

	}

	@Override
	public void stop(Future<Void> stopFuture) {
		logger.info("Stopping HTTP server...");
		stopFuture.complete();
	}

	private void serviceHandler(RoutingContext routingContext) {
		MetricsManager.getInstance().getCounter(SERVICE_NAME, COUNTER_NAME_GETS).inc();

		vertx.eventBus().send(Service1.EB_SERVICE_ADDRESS, "Get page",
				messageAsyncResult -> commonServiceHandler(routingContext, messageAsyncResult));
	}

	private void commonServiceHandler(RoutingContext routingContext, AsyncResult<Message<Object>> ar) {
		HttpServerResponse response = routingContext.response();
		if (ar.succeeded()) {
			handleMessage(response, ar.result());
		} else {
			handleErrorMessage(response, (ReplyException) ar.cause());
		}
	}

	private void handleMessage(HttpServerResponse response, Message<Object> message) {
		Map<String, String> results = new LinkedHashMap<>();
		results.put("response", message.body().toString());
		response
				.setStatusCode(400)
				.putHeader("content-type", "application/json")
				.end(Json.encodePrettily(results));
	}

	private void handleErrorMessage(HttpServerResponse response, ReplyException exception) {
		int errCode = exception.failureCode();
		String errMsg = exception.getMessage();
		Map<String, String> results = new LinkedHashMap<>();
		results.put("error", String.format("Failed getting page. Error code=%d, error message: %s", errCode, errMsg));

		response
				.setStatusCode(400)
				.putHeader("content-type", "application/json")
				.end(Json.encodePrettily(results));
	}

}
