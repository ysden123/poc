/*
 * Copyright (c) 2018, William Hill Online. All rights reserved
 */
package com.stulsoft.kafkaj.stream.wordcount;

import com.stulsoft.kafkaj.Common;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.stulsoft.kafkaj.Commit.Commit;

/**
 * @author Yuriy Stul.
 */
public class WordCountResultReader {
    private static Logger logger = LoggerFactory.getLogger(WordCountResultReader.class);
    private boolean continueExecuting = false;

    private Future<Void> start(final ExecutorService executor) {
        return executor.submit(() -> {
            continueExecuting = true;
            while (continueExecuting) {
                Properties props = new Properties();
                props.put("bootstrap.servers", Common.KAFKA_HOSTS);
                props.put("group.id", "wordCountResultReader");
//                props.put("enable.auto.commit", enabledAutoCommit.toString());
//                props.put("auto.offset.reset", autoOffsetRest.toString());
                props.put("auto.commit.interval.ms", "1000");
                props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
                props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
                KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

                consumer.subscribe(Collections.singletonList(Common.WORD_COUNT_OUTPUT_TOPIC));
                while (continueExecuting) {
                    ConsumerRecords<String, String> records = consumer.poll(1000);
                    records.forEach(record -> {
                        String resultText = String.format("Received message.\n\tPartition=%d, offset=%d, topic=\"%s\", key=%s, value=%s",
                                record.partition(),
                                record.offset(),
                                record.topic(),
                                record.key(),
                                record.value());
                        logger.info(resultText);
                    });
                    if (!records.isEmpty()) {
                        consumer.commitSync();
                    }
                }
                consumer.close();
            }
            return null;
        });
    }

    private Future<Void> stop(final ExecutorService executor) {
        return executor.submit(() -> {
            continueExecuting = false;
            return null;
        });
    }

    public static void main(String[] args) {
        logger.info("Started WordCountResultReader");
        WordCountResultReader reader = new WordCountResultReader();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Void> startedReader = reader.start(executor);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a line to exit");

        scanner.nextLine();
        reader.stop(executor);
        scanner.close();
        try {
            startedReader.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        executor.shutdown();
        logger.info("Stopped WordCountResultReader");
    }
}
