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
    KafkaClient client;

    private Application() {
        logger.info("==>Application");
        executorService = Executors.newFixedThreadPool(10);
        logger.info("<==Application");
    }

    private ExecutorService getExecutorService() {
        return executorService;
    }

    private void start() {
        logger.info("==>start");
        client = new KafkaClient(executorService);
        client.addHandler("test1Topic", Handler1.class);
        executorService.submit(client);
        logger.info("<==start");
    }

    private void stop() {
        client.stop();
    }

    public static void main(String[] args) {
        logger.info("==>main");
        final Application application = new Application();

        application.start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                application.stop();
                try {
                    Thread.sleep(1000);
                } catch (Exception ignore) {
                }
                application.getExecutorService().shutdown();
            }
        });

        logger.info("<==main");
    }
}
