/*
 * Copyright (c) 2018, William Hill Online. All rights reserved
 */
package com.stulsoft.kafkaj.stream.wordcount;

import com.stulsoft.kafkaj.Common;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

/**
 * @author Yuriy Stul.
 */
public class WordCountProcessor {
    private static Logger logger = LoggerFactory.getLogger(WordCountProcessor.class);

    public static void main(String[] args) {
        logger.info("Started WordCountProcessor");

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "Word-count-processor");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, Common.KAFKA_HOSTS);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // sets "metadata.max.age.ms" to 1 second for consumer only
        props.put(StreamsConfig.consumerPrefix(ConsumerConfig.METADATA_MAX_AGE_CONFIG), 50);
        // sets "metadata.max.age.ms" to 1 second for producer only
        props.put(StreamsConfig.producerPrefix(ProducerConfig.METADATA_MAX_AGE_CONFIG), 50);

        StreamsConfig sc = new StreamsConfig(props);
        System.out.println("StreamsConfig" + sc.values());
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> source = builder.stream(Common.WORD_COUNT_INPUT_TOPIC);

        final Pattern pattern = Pattern.compile("\\W+");
        KStream counts = source.flatMapValues(value -> Arrays.asList(pattern.split(value.toLowerCase())))
                .map((key, value) -> {
                    logger.debug("value {}", value);
                    return new KeyValue<Object, Object>(value, value);
                })
                .filter((key, value) -> (!value.equals("the")))
                .groupByKey()
                .count(Materialized.as("CountStore"))
                .mapValues(value -> Long.toString(value)).toStream();
        counts.to(Common.WORD_COUNT_OUTPUT_TOPIC);

        final Topology topology = builder.build();
        KafkaStreams streams = new KafkaStreams(topology, props);

        final ExecutorService executor = Executors.newFixedThreadPool(2);

        Future<Void> startedStreams = start(executor, streams);

        System.out.println("Enter a line to exit");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        stop(executor, streams);

        try {
            startedStreams.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        executor.shutdown();
        logger.info("Stopped WordCountProcessor");
    }

    private static Future<Void> start(final ExecutorService executor, final KafkaStreams streams) {
        return executor.submit(() -> {
            streams.start();
            return null;
        });
    }

    private static void stop(final ExecutorService executor, final KafkaStreams streams) {
        executor.submit((Runnable) streams::close);
    }
}
