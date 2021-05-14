/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.poc.cache.manager;

import io.reactivex.rxjava3.core.Single;
import io.vertx.core.json.JsonArray;

import java.util.function.Supplier;

/**
 * @author Yuriy Stul
 */
public interface ICacheManagerRx {
    void updateCollection(final String collectionName, final JsonArray collection);

    JsonArray getCollection(final String collectionName);

    /**
     * Adds a collection supplier
     *
     * @param collectionName the collection name
     * @param initialDelay   the initial delay is sec
     * @param period         the update period is sec
     * @param supplier       the collection supplier
     */
    void addCollectionSupplier(
            final String collectionName,
            final long initialDelay,
            final long period,
            final Supplier<Single<JsonArray>> supplier);

    void start();
}
