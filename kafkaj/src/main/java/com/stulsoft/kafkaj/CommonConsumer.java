/*
 * Copyright (c) 2017, William Hill Online. All rights reserved
 */
package com.stulsoft.kafkaj;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Yuriy Stul.
 */
public class CommonConsumer implements Consumer {
    private static Logger logger = LoggerFactory.getLogger(CommonConsumer.class);
    private boolean continueExecuting = false;
    private final String groupId;
    private final AutoCommit enabledAutoCommit;
    private final AutoOffsetRest autoOffsetRest;
    private final Commit commit;
    private final String topic;

    private final ExecutorService executor;

    CommonConsumer(final String groupId, final AutoCommit enabledAutoCommit, final AutoOffsetRest autoOffsetRest,
                   final Commit commit, final String topic) {
        this.groupId = groupId;
        this.enabledAutoCommit = enabledAutoCommit;
        this.autoOffsetRest = autoOffsetRest;
        this.commit = commit;
        this.topic = topic;

        executor = Executors.newFixedThreadPool(2);
    }

    @Override
    public Future<Void> start() {
        logger.info("Starting CommonConsumer...");
        continueExecuting = true;
        return executor.submit(() -> {
            logger.info("Started CommonConsumer");
            try {
                Properties props = new Properties();
                props.put("bootstrap.servers", Common.KAFKA_HOSTS);
                props.put("group.id", groupId);
                props.put("enable.auto.commit", enabledAutoCommit.toString());
                props.put("auto.offset.reset", autoOffsetRest.toString());
                props.put("auto.commit.interval.ms", "1000");
                props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
                props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
                KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

                consumer.subscribe(Collections.singletonList(topic));
                while (continueExecuting) {
                    ConsumerRecords<String, String> records = consumer.poll(1000);
                    records.forEach(record -> {
                        String resultText = String.format("Received message.\n\tPartition=%d, offset=%d, topic=\"%s\"",
                                record.partition(),
                                record.offset(),
                                record.topic());
                        logger.info(resultText);
                    });

                    if (!records.isEmpty()) {
                        switch (commit) {
                            case Commit:
                                consumer.commitSync();
                                break;
                            default:
                                break;
                        }
                    }
                }

                consumer.close();
                executor.shutdown();
                logger.info("Stopped CommonConsumer");
            }
            catch(Exception e){
                e.printStackTrace();
            }
            return null;
        });
    }

    @Override
    public Future<Void> stop() {
        logger.info("Stopping CommonConsumer");
        return executor.submit(() -> {
            continueExecuting = false;
            return null;
        });
    }
}
