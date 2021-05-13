/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.poc.cache.manager;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("==>main");
        ICacheManager cacheManager = new CacheManagerInMemory();
        cacheManager.addCollectionProvider("collection1", 10, 30, collectionName -> {
            logger.info("collection provider for {}", collectionName);
            return new JsonArray()
                    .add(new JsonObject()
                            .put("col11", 123)
                            .put("col12", 345));
        });
        cacheManager.addCollectionProvider("collection2", 10, 15, collectionName -> {
            logger.info("collection provider for {}", collectionName);
            return new JsonArray()
                    .add(new JsonObject()
                            .put("col21", "text 1")
                            .put("col22", "text 2"));
        });

        cacheManager.start();

        try{
            Thread.sleep(25500);
            logger.info("collection1 {}", cacheManager.getCollection("collection1").encode());
            logger.info("collection2 {}", cacheManager.getCollection("collection2").encode());
        }catch(Exception ignore){}
    }
}
