/*
 * Copyright (c) 2017, William Hill Online. All rights reserved
 */
package com.stulsoft.kafkaj;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Yuriy Stul.
 */
public class CommonProducer implements Producer {
    private static Logger logger = LoggerFactory.getLogger(CommonProducer.class);
    private boolean continueExecuting = false;
    private final ExecutorService executor;
    private final String topic;
    private final long interval;
    private final Random random = new Random();

    CommonProducer(final String topic, final long interval) {
        this.topic = topic;
        this.interval = interval;
        executor = Executors.newFixedThreadPool(2);
    }

    @Override
    public Future<Void> start() {
        logger.info("Started CommonProducer");
        continueExecuting = true;
        return executor.submit(() -> {
            try {
                Properties props = new Properties();
                props.put("bootstrap.servers", Common.KAFKA_HOSTS);
                props.put("acks", "all");
                props.put("retries", 0);
                props.put("batch.size", 16384);
                props.put("linger.ms", 1);
                props.put("buffer.memory", 33554432);
                props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
                props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

                KafkaProducer<String, String> producer = new KafkaProducer<>(props);

                while (continueExecuting) {
                    sendMessage(producer);
                    Thread.sleep(interval);
                }

                producer.close();
                executor.shutdown();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        });
    }

    @Override
    public Future<Void> stop() {
        logger.info("Stopping CommonProducer...");
        return executor.submit(() -> {
            continueExecuting = false;
            return null;
        });
    }

    private void sendMessage(KafkaProducer<String, String> producer) {
        try {
            Future<RecordMetadata> future = producer.send(new ProducerRecord<>(topic, "theKey_" + random.nextInt(), "the value_" + random.nextInt()));
            logger.info("Sent message");
            try {
                RecordMetadata result = future.get(10, TimeUnit.SECONDS);
                String resultText = String.format("Succeeded send message. Offset is %d, partition is %d, topic is %s",
                        result.offset(), result.partition(), result.topic());
                logger.info(resultText);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
