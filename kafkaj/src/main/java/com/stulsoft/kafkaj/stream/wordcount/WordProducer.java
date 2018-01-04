/*
 * Copyright (c) 2018, William Hill Online. All rights reserved
 */
package com.stulsoft.kafkaj.stream.wordcount;

import com.stulsoft.kafkaj.Common;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Yuriy Stul.
 */
public class WordProducer {
    private static Logger logger = LoggerFactory.getLogger(WordProducer.class);

    public static void main(String[] args) {
        logger.info("Started WordProducer");
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

            System.out.println("Enter empty line to exit");
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String statement = scanner.nextLine();
                if (statement.length() == 0) break;

                try {
                    Future<RecordMetadata> future = producer.send(new ProducerRecord<>( Common.WORD_COUNT_INPUT_TOPIC, "statement", statement));
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

            producer.close();
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Stopped WordProducer");
    }
}
