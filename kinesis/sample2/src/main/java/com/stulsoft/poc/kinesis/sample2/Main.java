/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.poc.kinesis.sample2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatch.CloudWatchAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;
import software.amazon.kinesis.common.ConfigsBuilder;
import software.amazon.kinesis.common.InitialPositionInStream;
import software.amazon.kinesis.common.InitialPositionInStreamExtended;
import software.amazon.kinesis.common.KinesisClientUtil;
import software.amazon.kinesis.coordinator.Scheduler;
import software.amazon.kinesis.retrieval.polling.PollingConfig;

/**
 * @author Yuriy Stul
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Read messages from {} on {} region",
                AppConfig.streamName(),
                AppConfig.awsRegion());

        var credentialsProvider = new CredentialsProvider();
        var kinesisAsyncClient = KinesisClientUtil.createKinesisAsyncClient(KinesisAsyncClient
                .builder()
                .credentialsProvider(credentialsProvider)
                .region(Region.of(AppConfig.awsRegion()))
        );

        PollingConfig pollingConfig = new PollingConfig(AppConfig.streamName(), kinesisAsyncClient)
                .maxRecords(1000)
                .idleTimeBetweenReadsInMillis(5000);

        var dynamoClient = DynamoDbAsyncClient
                .builder()
                .credentialsProvider(credentialsProvider)
                .region(Region.of(AppConfig.awsRegion()))
                .build();

        var cloudWatchClient = CloudWatchAsyncClient
                .builder()
                .credentialsProvider(credentialsProvider)
                .region(Region.of(AppConfig.awsRegion()))
                .build();

        var configsBuilder = new ConfigsBuilder(AppConfig.streamName(),
                AppConfig.appName(),
                kinesisAsyncClient,
                dynamoClient,
                cloudWatchClient,
                AppConfig.workerId(),
                new RecordProcessorFactory());

        logger.debug("configsBuilder.tableName(): {}", configsBuilder.tableName());
        var retrievalConfig = configsBuilder
                .retrievalConfig()
                .retrievalSpecificConfig(pollingConfig)
                .initialPositionInStreamExtended(InitialPositionInStreamExtended.newInitialPosition(InitialPositionInStream.TRIM_HORIZON));

        logger.debug("configsBuilder.checkpointConfig(): {}", configsBuilder.checkpointConfig());
        logger.debug("configsBuilder.coordinatorConfig(): {}", configsBuilder.coordinatorConfig());
        logger.debug("configsBuilder.lifecycleConfig(): {}", configsBuilder.lifecycleConfig());
        logger.debug("configsBuilder.processorConfig(): {}", configsBuilder.processorConfig());
        logger.debug("retrievalConfig: {}", retrievalConfig);
        var scheduler = new Scheduler(
                configsBuilder.checkpointConfig(),
                configsBuilder.coordinatorConfig(),
                configsBuilder.leaseManagementConfig(),
                configsBuilder.lifecycleConfig(),
                configsBuilder.metricsConfig(),
                configsBuilder.processorConfig(),
                retrievalConfig);

        var schedulerThread = new Thread(scheduler);
        schedulerThread.setDaemon(true);
        logger.info("Starting consumer");
        schedulerThread.start();
    }
}
