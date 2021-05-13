/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.poc.cache.manager;

import io.vertx.core.json.JsonArray;

import java.util.function.Function;

/**
 * @author Yuriy Stul
 */
public interface ICacheManager {
    void updateCollection(final String collectionName, final JsonArray collection);

    JsonArray getCollection(final String collectionName);

    /**
     * Adds a collection provider
     *
     * @param collectionName the collection name
     * @param initialDelay   the initial delay is sec
     * @param period         the update period is sec
     * @param provider       the collection provider
     */
    void addCollectionProvider(
            final String collectionName,
            final long initialDelay,
            final long period,
            Function<String, JsonArray> provider);

    void start();
}
