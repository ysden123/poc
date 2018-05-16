package com.stulsoft.prometheus.server1;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * @author Yuriy Stul
 * @since 5/3/2018
 */
public class Service extends AbstractVerticle {
    private static Logger logger = LoggerFactory.getLogger(Service.class);
    private Random random = new Random(1234);

    @Override
    public void start(Future<Void> startFuture) {
        logger.info("Starting Service...");

        vertx.eventBus().consumer("serviceAddress", this::handleMessage);
        startFuture.complete();
    }

    @Override
    public void stop(Future<Void> stopFuture) {
        logger.info("Stopping Service...");
        stopFuture.complete();
    }

    private void handleMessage(Message<String> message) {
        logger.info("Received message {} from {}", message.body(), message.address());
        if (random.nextBoolean())
            message.reply("My reply");
        else
            message.fail(1, "Something went wrong");
    }

}
