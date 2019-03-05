/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.java7.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yuriy Stul
 */
public class KafkaClientRunner {
    private static Logger logger = LoggerFactory.getLogger(KafkaClientRunner.class);

    public static void main(String[] args) {
        logger.info("==>main");
        KafkaClient client = new KafkaClient();
        client.addHandler(new Handler1("test1Topic"));
        client.run();
        logger.info("<==main");
    }
}
