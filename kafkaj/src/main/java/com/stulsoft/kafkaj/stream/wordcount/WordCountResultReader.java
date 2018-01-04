/*
 * Copyright (c) 2018, William Hill Online. All rights reserved
 */
package com.stulsoft.kafkaj.stream.wordcount;

import com.stulsoft.kafkaj.Common;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Yuriy Stul.
 */
public class WordCountResultReader {
    private static Logger logger = LoggerFactory.getLogger(WordCountResultReader.class);
    private boolean continueExecuting = false;

    private Future<Void> start(final ExecutorService executor) {
        return executor.submit(() -> {
            logger.info("Started WordCountResultReader");
            Map<String, String> words = new TreeMap<>();
            continueExecuting = true;
            while (continueExecuting) {
                Properties props = new Properties();
                props.put("bootstrap.servers", Common.KAFKA_HOSTS);
                props.put("group.id", "wordCountResultReader");
//                props.put("enable.auto.commit", enabledAutoCommit.toString());
//                props.put("auto.offset.reset", autoOffsetRest.toString());
                props.put("auto.offset.reset", "earliest");
                props.put("auto.commit.interval.ms", "500");
                props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
                props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
                KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

                consumer.subscribe(Collections.singletonList(Common.WORD_COUNT_OUTPUT_TOPIC));

                consumer.poll(500);
                consumer.seekToBeginning(Collections.singletonList(new TopicPartition(Common.WORD_COUNT_OUTPUT_TOPIC, 0)));

                while (continueExecuting) {
                    ConsumerRecords<String, String> records = consumer.poll(500);
                    records.forEach(record -> words.put(record.key(), record.value()));
                    if (!records.isEmpty())
                        showResults(words);
                }
                consumer.close();
            }
            logger.info("Stopped WordCountResultReader");
            return null;
        });
    }

    private Future<Void> stop(final ExecutorService executor) {
        return executor.submit(() -> {
            continueExecuting = false;
            return null;
        });
    }

    private void showResults(final Map<String, String> words) {
        if (!words.isEmpty()) {
            List<Map.Entry<String, String>> sortedEntries = new ArrayList<>(words.entrySet());
            sortedEntries.sort(Comparator.comparingInt(o -> -Integer.parseInt(o.getValue())));
            logger.info("\n");
            sortedEntries.forEach(e -> logger.info("{} {}", e.getKey(), e.getValue()));
        }
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
