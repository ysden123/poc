/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.poc.kinesis.sample2;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;
import software.amazon.awssdk.services.kinesis.model.PutRecordsRequest;
import software.amazon.awssdk.services.kinesis.model.PutRecordsRequestEntry;
import software.amazon.kinesis.common.KinesisClientUtil;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * Sends messages to Kinesis data stream for testing a consumer.
 *
 * @author Yuriy Stul
 */
public class TestProducer {
    private static final Logger logger = LoggerFactory.getLogger(TestProducer.class);

    public static void main(String[] args) {
        logger.info("==>main");
        int iterations = 5;
        int n = 10;
        logger.info("Puts {} messages into {} on {} region",
                n,
                AppConfig.streamName(),
                AppConfig.awsRegion());
        var credentialsProvider = new CredentialsProvider();

        var kinesisAsyncClient = KinesisClientUtil.createKinesisAsyncClient(KinesisAsyncClient
                .builder()
                .credentialsProvider(credentialsProvider)
                .region(Region.of(AppConfig.awsRegion()))
        );

        for (int iteration = 1; iteration <= iterations; ++iteration) {
            logger.info("Iteration {}", iteration);
            var records = new LinkedList<PutRecordsRequestEntry>();
            for (int i = 1; i <= n; ++i) {
//                var theMinute = minute();
                var theSecond = second();
                var entry = PutRecordsRequestEntry.builder()
//                        .partitionKey(theMinute)
                        .partitionKey(theSecond)
//                        .data(generateRecord(theMinute, i))
                        .data(generateRecord(theSecond, i))
                        .build();
                records.add(entry);
            }

            var putRecordsRequest = PutRecordsRequest
                    .builder()
                    .streamName(AppConfig.streamName())
                    .records(records)
                    .build();

            try {
                var result = kinesisAsyncClient.putRecords(putRecordsRequest);
                var response = result.get(10, TimeUnit.SECONDS);
                if (result != null) {
                    logger.info("Succeeded {} from {}",
                            (putRecordsRequest.records().size() - response.failedRecordCount()),
                            putRecordsRequest.records().size());
                } else {
                    logger.error("No result from Kinesis");
                }
            } catch (Exception ex) {
                logger.error("Failed put records: " + ex.getMessage());
            }

            if (iteration < iterations) {
                try {
                    Thread.sleep(AppConfig.checkPointInterval() / 3);
                } catch (Exception ignore) {
                }
            }
        }
        logger.info("<==main");
    }


    private static String minute() {
        var calendar = Calendar.getInstance();
        var minute = String.format("%04d%02d%02d%02d%02d",
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE));
        logger.debug("minute = {}", minute);

//        var minute = "0";
        return minute;
    }


    private static String second() {
        /*
        var calendar = Calendar.getInstance();
        var second = String.format("%04d%02d%02d%02d%02d%02d",
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND));
        logger.debug("second = {}", second);
        */
        var second = "0";
        return second;
    }

    private static SdkBytes generateRecord(String minute, int i) {
        var object = new JSONObject();
        object
                .put("minute", minute)
                .put("i", i);
        return SdkBytes.fromUtf8String(object.toString());
    }
}
