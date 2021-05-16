/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.poc.cache.manager.vertticle;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * @author Yuriy Stul
 */
public class AppVertx {
    private static final Logger logger = LoggerFactory.getLogger(AppVertx.class);

    public static void main(String[] args) throws InterruptedException {
        logger.info("==>main");

        var vertx = Vertx.vertx();

        vertx.deployVerticle(
                new CacheManagerVerticle(),
                ar -> vertx.eventBus().send(CacheManagerVerticle.EB_START_CACHE_UPDATE_ADDRESS, null));

        var countDownLatch = new CountDownLatch(2);
        vertx.setTimer(21000,
                __ -> vertx.eventBus()
                        .<JsonArray>request(
                                CacheManagerVerticle.EB_GET_COLLECTION_ADDRESS,
                                new JsonObject().put("collectionName", "collection1"),
                                ar -> {
                                    logger.info(ar.result().body().encode());
                                    countDownLatch.countDown();
                                }));

        vertx.setTimer(22000,
                __ -> vertx.eventBus()
                        .<JsonArray>request(
                                CacheManagerVerticle.EB_GET_COLLECTION_ADDRESS,
                                new JsonObject().put("collectionName", "collection2"),
                                ar -> {
                                    logger.info(ar.result().body().encode());
                                    countDownLatch.countDown();
                                }));

        countDownLatch.await();
        vertx.close();
    }
}
