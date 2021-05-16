/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.poc.cache.manager.vertticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Yuriy Stul
 */
public class CacheManagerVerticle extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(CacheManagerVerticle.class);

    public static final String EB_START_CACHE_UPDATE_ADDRESS = CacheManagerVerticle.class.getName() + "_startCacheUpdate";
    public static final String EB_GET_COLLECTION_ADDRESS = CacheManagerVerticle.class.getName() + "_getCollection";
    public static final String EB_UPDATE_COLLECTION_ADDRESS = CacheManagerVerticle.class.getName() + "_updateCollection";

    private final Map<String, JsonArray> cache = new ConcurrentHashMap<>();

    @Override
    public void init(Vertx vertx, Context context) {
        super.init(vertx, context);
    }

    @Override
    public void start() throws Exception {
        super.start();

        vertx.eventBus().consumer(EB_START_CACHE_UPDATE_ADDRESS, this::handleStart);
        vertx.eventBus().consumer(EB_GET_COLLECTION_ADDRESS, this::handleGetCollection);
        vertx.eventBus().consumer(EB_UPDATE_COLLECTION_ADDRESS, this::handleUpdateCollection);
    }

    private void handleStart(Message<?> message) {
        logger.info("==>handleStart");

        supplier1();
        vertx.setPeriodic(10000, __ -> supplier1());

        supplier2();
        vertx.setPeriodic(15000, __ -> supplier2());

        message.reply(new JsonObject());
    }

    private void handleGetCollection(Message<JsonObject> message) {
        message.reply(cache.getOrDefault(message.body().getString("collectionName"), new JsonArray()));
    }

    private void handleUpdateCollection(Message<JsonObject> message) {
        var collectionName = message.body().getString("collectionName");
        var collection = message.body().getJsonArray("collection");
        cache.put(collectionName, collection);
    }

    private void supplier1() {
        logger.info("Supplier 1");
        vertx.eventBus().send(EB_UPDATE_COLLECTION_ADDRESS, new JsonObject()
                .put("collectionName", "collection1")
                .put("collection", new JsonArray()
                        .add(new JsonObject()
                                .put("coll11", 123)
                                .put("coll12", 456)
                        ))
        );
    }

    private void supplier2() {
        logger.info("Supplier 2");
        vertx.eventBus().send(EB_UPDATE_COLLECTION_ADDRESS, new JsonObject()
                .put("collectionName", "collection2")
                .put("collection", new JsonArray()
                        .add(new JsonObject()
                                .put("coll21", "abc")
                                .put("coll22", "def")
                        ))
        );
    }
}
