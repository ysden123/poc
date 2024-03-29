/*
 * Copyright (c) 2021. StulSoft
 */

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.stulsoft.poc.websocket.ex1;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("==>main");
        var vertx = Vertx.vertx();

//        test1(vertx);
        test2(vertx);
    }

    private static void test1(final Vertx vertx) {
        logger.info("==>test1");
        vertx.deployVerticle(new WebSocketServerVerticle(),
                (__) -> vertx.deployVerticle(new WebSocketClientVerticle()));
    }

    // For broadcasting
    private static void test2(final Vertx vertx) {
        logger.info("==>test2");
        vertx.deployVerticle(new WebSocketServer2Verticle(),
                (__) -> {
                    vertx.deployVerticle(
                            new WebSocketClient2Verticle(),
                            new DeploymentOptions()
                                    .setConfig(new JsonObject()
                                            .put("user", "user 1")));
                    vertx.deployVerticle(
                            new WebSocketClient2Verticle(),
                            new DeploymentOptions()
                                    .setConfig(new JsonObject()
                                            .put("user", "user 2")));
                });
    }
}
