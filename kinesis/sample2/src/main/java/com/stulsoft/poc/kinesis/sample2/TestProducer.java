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
 * @author Yuriy Stul
 */
public class TestProducer {
    private static final Logger logger = LoggerFactory.getLogger(TestProducer.class);

    public static void main(String[] args) {
        logger.info("==>main");
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


        var records = new LinkedList<PutRecordsRequestEntry>();
        for (int i = 1; i <= n; ++i) {
            var theMinute = minute();
            var entry = PutRecordsRequestEntry.builder()
                    .partitionKey(theMinute)
                    .data(generateRecord(theMinute, i))
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
        return minute;
    }

    private static SdkBytes generateRecord(String minute, int i) {
        var object = new JSONObject();
        object
                .put("minute", minute)
                .put("i", i);
        return SdkBytes.fromUtf8String(object.toString());
    }
}
