package com.stulsoft.prometheus.server1;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
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
 * @author Yuriy Stul
 * @since 14 May 2018
 *
 */
public class HttpServer  extends AbstractVerticle  {
	private static Logger logger = LoggerFactory.getLogger(HttpServer.class);
	
	private static CollectorRegistry registry;
	private static MetricsHandler metricsHandler;
	private static Counter getIndexCounter;
	private static Counter getIndexSuccededCounter;
	private static Counter getIndexFailedCounter;


    @Override
    public void start(Future<Void> startFuture) {
    	logger.info("Starting HTTP server...");
    	
    	registry = new CollectorRegistry();
    	metricsHandler = new MetricsHandler(registry);
    	
    	getIndexCounter = Counter
    			.build("wp_server1_get","WebPals counter for get method in Server1")
    			.register(registry);
    	getIndexSuccededCounter = Counter
    			.build("wp_server1_get_succeded","WebPals counter for succeded get method in Server1")
    			.register(registry);
    	getIndexFailedCounter = Counter
    			.build("wp_server1_get_failed","WebPals counter for failed get method in Server1")
    			.register(registry);
    	
    	io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);
        router.get("/").handler(this::serviceHandler);
        router.get("/metrics").handler(metricsHandler::handle);
        
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
        logger.info("Handling service...");
        
        getIndexCounter.inc();

        vertx.eventBus().send("serviceAddress", "Get page",
                messageAsyncResult -> commonServiceHandler(routingContext, messageAsyncResult));
    }
    
    private void commonServiceHandler(RoutingContext routingContext, AsyncResult<Message<Object>> ar) {
        HttpServerResponse response = routingContext.response();
        if (ar.succeeded()) {
        	getIndexSuccededCounter.inc();
            handleMessage(response, ar.result());
        } else {
        	getIndexFailedCounter.inc();
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
