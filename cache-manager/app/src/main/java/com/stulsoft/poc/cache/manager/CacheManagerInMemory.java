/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.poc.cache.manager;

import io.vertx.core.json.JsonArray;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author Yuriy Stul
 */
public class CacheManagerInMemory implements ICacheManager {

    static class SupplierData {
        final String collectionName;
        final long initialDelay;
        final long period;
        final Supplier<JsonArray> supplier;

        public SupplierData(String collectionName, long initialDelay, long period, Supplier<JsonArray> supplier) {
            this.collectionName = collectionName;
            this.initialDelay = initialDelay;
            this.period = period;
            this.supplier = supplier;
        }
    }

    private final Map<String, JsonArray> cache;
    private final Map<String, SupplierData> suppliers;
    private final ScheduledExecutorService executors;

    public CacheManagerInMemory() {
        cache = new HashMap<>();
        suppliers = new HashMap<>();
        executors = Executors.newScheduledThreadPool(4);
    }

    @Override
    public void updateCollection(String collectionName, JsonArray collection) {
        synchronized (cache) {
            cache.put(collectionName, collection);
        }
    }

    @Override
    public JsonArray getCollection(String collectionName) {
        synchronized (cache) {
            return cache.get(collectionName);
        }
    }

    @Override
    public void addCollectionSupplier(String collectionName, long initialDelay, long period, Supplier<JsonArray> supplier) {
        var providerData = new SupplierData(collectionName, initialDelay, period, supplier);
        suppliers.put(collectionName, providerData);
    }

    @Override
    public void start() {
        suppliers.forEach((collectionName, provider) -> executors.scheduleAtFixedRate(
                () -> updateCollection(collectionName, provider.supplier.get()),
                provider.initialDelay,
                provider.period,
                TimeUnit.SECONDS));
    }
}