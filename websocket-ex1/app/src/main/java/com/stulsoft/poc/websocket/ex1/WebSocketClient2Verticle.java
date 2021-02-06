/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.poc.websocket.ex1;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yuriy Stul
 */
public class WebSocketClient2Verticle extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketClient2Verticle.class);
    private WebSocket webSocket;
    private String user;

    @Override
    public void start() {
        user = config().getString("user");
        startClient(vertx);
        logger.info("WebSocketClientVerticle has started");
    }

    private void startClient(final Vertx vertx) {
        HttpClient client = vertx.createHttpClient();

        client.webSocket(8080, "localhost", "/",
                ctx -> {
                    webSocket = ctx.result();
                    webSocket.textMessageHandler(this::handler);
                    vertx.setTimer(3000,
                            l -> webSocket.writeTextMessage("Get data with parameter 1 for user ".concat(user)));
                });
    }

    private void handler(final String msg) {
        logger.info("==>handler");
        logger.info("Client for user: {}, received from server: {}", user, msg);
    }
}
