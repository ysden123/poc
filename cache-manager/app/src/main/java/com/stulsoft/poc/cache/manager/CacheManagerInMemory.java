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
import java.util.function.Function;

/**
 * @author Yuriy Stul
 */
public class CacheManagerInMemory implements ICacheManager {

    static class ProviderData {
        final String collectionName;
        final long initialDelay;
        final long period;
        final Function<String, JsonArray> updater;

        public ProviderData(String collectionName, long initialDelay, long period, Function<String, JsonArray> updater) {
            this.collectionName = collectionName;
            this.initialDelay = initialDelay;
            this.period = period;
            this.updater = updater;
        }
    }

    private final Map<String, JsonArray> cache;
    private final Map<String, ProviderData> providers;
    private final ScheduledExecutorService executors;

    public CacheManagerInMemory() {
        cache = new HashMap<>();
        providers = new HashMap<>();
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
    public void addCollectionProvider(String collectionName, long initialDelay, long period, Function<String, JsonArray> provider) {
        var updaterData = new ProviderData(collectionName, initialDelay, period, provider);
        providers.put(collectionName, updaterData);
    }

    @Override
    public void start() {
        providers.forEach((collectionName, updater) -> executors.scheduleAtFixedRate(
                () -> updateCollection(collectionName, updater.updater.apply(collectionName)),
                updater.initialDelay,
                updater.period,
                TimeUnit.SECONDS));
    }
}
