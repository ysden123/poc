/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.java7.kafka.agent;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.ExecutorService;

/**
 * @author Yuriy Stul
 */
public class KafkaClient implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(KafkaClient.class);

    private ExecutorService executorService;
    private KafkaConsumer<String, String> consumer;
    private HashMap<String, String> handlers = new HashMap<>();
    private boolean toRun;
    private long pollInterval;

    public KafkaClient(final ExecutorService executorService) {
        logger.info("==>KafkaClient");

        toRun = true;
        pollInterval = 100;
        this.executorService = executorService;

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.offset.reset", "latest");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        consumer = new KafkaConsumer<>(props);

//        consumer.subscribe(Collections.singleton("test1Topic"));
        logger.info("<==KafkaClient");
    }

    public void addHandler(String topic, String className) {
        logger.debug("Adding {} handler for {}", className, topic);
        handlers.put(topic, className);
    }

    public void stop(){
        toRun = false;
    }

    @Override
    public void run() {
        consumer.subscribe(handlers.keySet());
        while(toRun){
            ConsumerRecords<String, String> records = consumer.poll(pollInterval);
            for (ConsumerRecord<String, String> record : records) {
                String handlerClassName = handlers.get(record.topic());
                if (handlerClassName != null) {
                    try {
                        Class<?> clazz = Class.forName(handlerClassName);
                        Constructor<?> ctor = clazz.getConstructor(ConsumerRecord.class);
                        Runnable instance = (Runnable)ctor.newInstance(record);
                        executorService.submit(instance);
                    }catch(Exception ex){
                        logger.error(ex.getMessage(), ex);
                    }
                } else {
                    logger.error("No handler found for topic {}", record.topic());
                }
            }
        }
    }
}
