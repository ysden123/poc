/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.poc.websocket.ex1;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yuriy Stul
 */
public class WebSocketClientVerticle extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketClientVerticle.class);

    @Override
    public void start() {
        startClient(vertx);
        logger.info("WebSocketClientVerticle has started");
    }

    private void startClient(final Vertx vertx) {
        HttpClient client = vertx.createHttpClient();

        client.webSocket(8080, "localhost", "/", this::webSocketHandler);
    }

    private void webSocketHandler(final AsyncResult<WebSocket> ctx) {
        logger.info("==>webSocketHandler");
        ctx.result().textMessageHandler(msg -> {
            logger.info("Received from server: {}", msg);

            ctx.result().writeTextMessage("pong to server");
        });
    }

}
