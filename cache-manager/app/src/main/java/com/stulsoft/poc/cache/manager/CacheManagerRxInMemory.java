/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.poc.cache.manager;

import io.reactivex.rxjava3.core.Single;
import io.vertx.core.json.JsonArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author Yuriy Stul
 */
public class CacheManagerRxInMemory implements ICacheManagerRx {
    private static final Logger logger = LoggerFactory.getLogger(CacheManagerRxInMemory.class);

    static class SupplierData {
        final String collectionName;
        final long initialDelay;
        final long period;
        final Supplier<Single<JsonArray>> supplier;

        public SupplierData(String collectionName, long initialDelay, long period, Supplier<Single<JsonArray>> supplier) {
            this.collectionName = collectionName;
            this.initialDelay = initialDelay;
            this.period = period;
            this.supplier = supplier;
        }
    }

    private final Map<String, JsonArray> cache;
    private final Map<String, SupplierData> suppliers;
    private final ScheduledExecutorService executors;

    public CacheManagerRxInMemory() {
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
    public void addCollectionSupplier(String collectionName, long initialDelay, long period, Supplier<Single<JsonArray>> supplier) {
        var providerData = new SupplierData(collectionName, initialDelay, period, supplier);
        suppliers.put(collectionName, providerData);
    }

    @Override
    public void start() {
        suppliers.forEach((collectionName, supplier) -> executors.scheduleAtFixedRate(
                () -> {
                    supplier.supplier.get().subscribe(
                            collection -> updateCollection(collectionName, collection),
                            error -> logger.error("Failed getting " + collectionName + " collection: " + error.getMessage())
                    );
                },
                supplier.initialDelay,
                supplier.period,
                TimeUnit.SECONDS
        ));
    }
}
