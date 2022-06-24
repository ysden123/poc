/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.kafkaj.stream.wordcount;

import com.stulsoft.kafkaj.Common;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * @author Yuriy Stul
 */
public class WordCountReset {
    private static final Logger logger = LoggerFactory.getLogger(WordCountReset.class);

    public static void main(String[] args) {
        logger.info("Started WordCountReset");

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "Word-count-processor");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, Common.KAFKA_HOSTS);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> source = builder.stream(Common.WORD_COUNT_INPUT_TOPIC);

        final Pattern pattern = Pattern.compile("\\W+");
        KStream<Object, String> counts = source.flatMapValues(value -> Arrays.asList(pattern.split(value.toLowerCase())))
                .map((key, value) -> {
                    logger.debug("key=" + key + ". value=" + value);
                    return new KeyValue<Object, Object>(value, value);
                })
                .filter((key, value) -> (!value.equals("the")))
                .groupByKey()
                .count(Materialized.as("CountStore"))
                .mapValues(value -> Long.toString(value)).toStream();
        counts.to(Common.WORD_COUNT_OUTPUT_TOPIC);

        final Topology topology = builder.build();
        KafkaStreams streams = new KafkaStreams(topology, props);

        // This is for reset to work. Don't use in production - it causes the app to re-load
        // the state from Kafka on every start
        streams.cleanUp();

        logger.info("Finished WordCountReset");
    }
}
