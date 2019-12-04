/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.poc.kinesis.sample1;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.InitialPositionInStream;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yuriy Stul
 * @since 1.0.0
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /*
     * Before running the code:
     *      Fill in your AWS access credentials in the provided credentials
     *      file template, and be sure to move the file to the default location
     *      (~/.aws/credentials) where the sample code will load the
     *      credentials from.
     *      https://console.aws.amazon.com/iam/home?#security_credential
     *
     * WARNING:
     *      To avoid accidental leakage of your credentials, DO NOT keep
     *      the credentials file in your source directory.
     */

    // Initial position in the stream when the application starts up for the first time.
    // Position can be one of LATEST (most recent data) or TRIM_HORIZON (oldest available data)
    private static final InitialPositionInStream SAMPLE_APPLICATION_INITIAL_POSITION_IN_STREAM =
            InitialPositionInStream.TRIM_HORIZON;
//            InitialPositionInStream.LATEST;

    private static AWSCredentialsProvider credentialsProvider;

    private static void init() {
        // Ensure the JVM will refresh the cached IP values of AWS resources (e.g. service endpoints).
        java.security.Security.setProperty("networkaddress.cache.ttl", "60");

        /*
         * The ProfileCredentialsProvider will return your [default]
         * credential profile by reading from the credentials file located at
         * (~/.aws/credentials).
         */
        credentialsProvider = new CredentialsProvider();
        try {
            credentialsProvider.getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
                    + "Please make sure that your credentials file is at the correct "
                    + "location (~/.aws/credentials), and is in valid format.", e);
        }
    }

    public static void main(String[] args) throws Exception {
        init();

        if (args.length == 1 && "delete-resources".equals(args[0])) {
            deleteResources();
            return;
        }

//        String workerId = InetAddress.getLocalHost().getCanonicalHostName() + ":" + UUID.randomUUID();
        String workerId = AppConfig.workerId();
        KinesisClientLibConfiguration kinesisClientLibConfiguration =
                new KinesisClientLibConfiguration(AppConfig.appName(),
                        AppConfig.streamName(),
                        credentialsProvider,
                        workerId)
                        .withInitialPositionInStream(SAMPLE_APPLICATION_INITIAL_POSITION_IN_STREAM)
                        .withRegionName(AppConfig.awsRegion())
                        .withMaxRecords(AppConfig.maxRecords())
                        .withShardSyncIntervalMillis(AppConfig.syncInterval());
/**/

        logger.debug("getIdleTimeBetweenReadsInMillis() = {}, getListShardsBackoffTimeInMillis() = {}, getMaxRecords()= {}, getShardSyncIntervalMillis()={}",
                kinesisClientLibConfiguration.getIdleTimeBetweenReadsInMillis(),
                kinesisClientLibConfiguration.getListShardsBackoffTimeInMillis(),
                kinesisClientLibConfiguration.getMaxRecords(),
                kinesisClientLibConfiguration.getShardSyncIntervalMillis()
        );
/**/

        final IRecordProcessorFactory recordProcessorFactory = new AmazonKinesisApplicationRecordProcessorFactory();
        Worker worker = new Worker.Builder()
                .recordProcessorFactory(recordProcessorFactory)
                .config(kinesisClientLibConfiguration)
                .build();

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                logger.info("(2) Call worker shutdown ...");
                worker.shutdown();
            }
        });

        logger.info("Running {} to process stream {} as worker {}",
                AppConfig.appName(),
                AppConfig.streamName(),
                workerId);

        int exitCode = 0;
        try {
            worker.run();
        } catch (Throwable t) {
            logger.error("Caught throwable while processing data. " + t.getMessage(), t);
            exitCode = 22;
        }finally {
            logger.info("(1) Call worker shutdown ...");
            worker.shutdown();
        }
        System.exit(exitCode);
    }

    public static void deleteResources() {
        // Delete the stream
/*
        AmazonKinesis kinesis = AmazonKinesisClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withRegion(AppConfig.awsRegion())
                .build();

        logger.info("Deleting the Amazon Kinesis stream used by the sample. Stream Name = {}",
                AppConfig.streamName());
        try {
            kinesis.deleteStream(AppConfig.streamName());
        } catch (ResourceNotFoundException ex) {
            // The stream doesn't exist.
        }

        // Delete the table
        AmazonDynamoDB dynamoDB = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withRegion(AppConfig.awsRegion())
                .build();
        logger.info("Deleting the Amazon DynamoDB table used by the Amazon Kinesis Client Library. Table Name = {}",
                AppConfig.appName());
        try {
            dynamoDB.deleteTable(AppConfig.appName());
        } catch (com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException ex) {
            // The table doesn't exist.
        }
*/
    }
}
