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

import java.util.Random;

/**
 * @author Yuriy Stul
 */
public class WebSocketServerVerticle extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketServerVerticle.class);

    @Override
    public void start() {
        startServer(vertx);
        logger.info("WebSocketServerVerticle has started");
    }

    private void startServer(final Vertx vertx) {
        HttpServer server = vertx.createHttpServer();
        server
                .webSocketHandler(this::webSocketHandler)
                .listen(8080);
    }

    private void webSocketHandler(ServerWebSocket serverWebSocket) {
        logger.info("==>webSocketHandler");
        serverWebSocket.writeTextMessage("ping from server");
        serverWebSocket.textMessageHandler(msg -> {
            logger.info("msg from client: {}", msg);
            if ((new Random()).nextInt(100) == 0)
                serverWebSocket.close();
            else
                serverWebSocket.writeTextMessage("Replying on " + msg);
        });
    }
}
