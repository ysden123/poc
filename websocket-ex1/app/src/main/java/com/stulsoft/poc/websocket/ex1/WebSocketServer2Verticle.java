/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.poc.websocket.ex1;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.ServerWebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Demonstrates usage WebSocket for publishing messages for all clients.
 *
 * @author Yuriy Stul
 */
public class WebSocketServer2Verticle extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer2Verticle.class);
    private final HashMap<String, ServerWebSocket> serverWebSockets = new HashMap<>();

    @Override
    public void start() {
        startServer(vertx);
        logger.info("WebSocketServerVerticle has started");

        vertx.setTimer(30000, this::broadcast);
    }

    private void startServer(final Vertx vertx) {
        HttpServer server = vertx.createHttpServer();
        server
                .webSocketHandler(this::webSocketHandler)
                .listen(8080);
    }

    private void webSocketHandler(ServerWebSocket serverWebSocket) {
        logger.info("==>webSocketHandler");
        if (!serverWebSockets.containsKey(serverWebSocket.textHandlerID())) {
            logger.debug("serverWebSocket was registered for {}", serverWebSocket.textHandlerID());
            serverWebSockets.put(serverWebSocket.textHandlerID(), serverWebSocket);
        }

        serverWebSocket.textMessageHandler(msg -> {
            logger.info("msg from client: {}", msg);
            serverWebSocket.writeTextMessage("Replying on " + msg);
        });
    }

    private void broadcast(long __) {
        logger.info("==>broadcast");
        serverWebSockets.values().forEach(sws -> sws.writeTextMessage("Broadcasting message from server"));
    }
}
