/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.java7.kafka.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Yuriy Stul
 */
public class Application {
    private static Logger logger = LoggerFactory.getLogger(Application.class);

    private ExecutorService executorService;

    private Application() {
        logger.info("==>Application");
        executorService = Executors.newFixedThreadPool(10);
        logger.info("<==Application");
    }

    private void start() {
        logger.info("==>start");
        KafkaClient client = new KafkaClient(executorService);
        client.addHandler("test1Topic", "com.stulsoft.java7.kafka.agent.Handler1");
        executorService.submit(client);
        logger.info("<==start");
    }

    public static void main(String[] args) {
        logger.info("==>main");
        Application application = new Application();

        application.start();

        logger.info("<==main");
    }
}
