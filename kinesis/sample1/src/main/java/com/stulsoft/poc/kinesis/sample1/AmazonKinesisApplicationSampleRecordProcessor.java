/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.poc.kinesis.sample1;

import com.amazonaws.services.kinesis.clientlibrary.exceptions.InvalidStateException;
import com.amazonaws.services.kinesis.clientlibrary.exceptions.ShutdownException;
import com.amazonaws.services.kinesis.clientlibrary.exceptions.ThrottlingException;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorCheckpointer;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.ShutdownReason;
import com.amazonaws.services.kinesis.model.Record;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Processes records and checkpoints progress.
 *
 * @author Yuriy Stul
 */
public class AmazonKinesisApplicationSampleRecordProcessor implements IRecordProcessor {
    private static final Logger logger = LoggerFactory.getLogger(AmazonKinesisApplicationSampleRecordProcessor.class);

    private String kinesisShardId;

    // Backoff and retry settings
//    private static final long BACKOFF_TIME_IN_MILLIS = 3000L;
    private static final long BACKOFF_TIME_IN_MILLIS = 1000L;
    private static final int NUM_RETRIES = 10;

    // Checkpoint about once a minute
    private static final long CHECKPOINT_INTERVAL_MILLIS = 15000L;
    private long nextCheckpointTimeInMillis;

    private final CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(String shardId) {
        logger.info("Initializing record processor for shard: {}", shardId);
        this.kinesisShardId = shardId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processRecords(List<Record> records, IRecordProcessorCheckpointer checkpointer) {
        logger.info("Processing {} records from {}", records.size(), kinesisShardId);

        // Process records and perform all exception handling.
        processRecordsWithRetries(records);

        // Checkpoint once every checkpoint interval.
        if (System.currentTimeMillis() > nextCheckpointTimeInMillis) {
            checkpoint(checkpointer);
            nextCheckpointTimeInMillis = System.currentTimeMillis() + CHECKPOINT_INTERVAL_MILLIS;
        }
    }

    /**
     * Process records performing retries as needed. Skip "poison pill" records.
     *
     * @param records Data records to be processed.
     */
    private void processRecordsWithRetries(List<Record> records) {
        for (Record record : records) {
            boolean processedSuccessfully = false;
            for (int i = 0; i < NUM_RETRIES; i++) {
                try {
                    //
                    // Logic to process record goes here.
                    //
                    processSingleRecord(record);

                    processedSuccessfully = true;
                    break;
                } catch (Throwable t) {
                    logger.error("Caught throwable while processing record " + record, t);
                }

                // backoff if we encounter an exception.
                try {
                    Thread.sleep(BACKOFF_TIME_IN_MILLIS);
                } catch (InterruptedException e) {
                    logger.debug("Interrupted sleep {}", e.getMessage());
                }
            }

            if (!processedSuccessfully) {
                logger.error("Couldn't process record {}. Skipping the record.", record);
            }
        }
    }

    /**
     * Process a single record.
     *
     * @param record The record to be processed.
     */
    private void processSingleRecord(Record record) {
        // TODO Add your own record processing logic here

        String data = null;
        try {
            // For this app, we interpret the payload as UTF-8 chars.
            data = decoder.decode(record.getData()).toString();
            logger.info("partition: {}, getSequenceNumber = {}", record.getPartitionKey(), record.getSequenceNumber());
            logger.info("data: {}", data);
            try {
                var dataObject = new JSONObject(data);
            }catch(JSONException ex){
                logger.error("Bad JSON: " + ex.getMessage());
            }
/*
            // Assume this record came from AmazonKinesisSample and log its age.
            long recordCreateTime = Long.parseLong(data.substring("testData-".length()));
            long ageOfRecordInMillis = System.currentTimeMillis() - recordCreateTime;
            logger.info("{}, {}, {}, created {} milliseconds ago.",
                    record.getSequenceNumber(), record.getPartitionKey(), data, ageOfRecordInMillis);
*/
        } catch (NumberFormatException e) {
            logger.info("Record does not match sample record format. Ignoring record with data; {}", data);
        } catch (CharacterCodingException e) {
            logger.error("Malformed data: " + data, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shutdown(IRecordProcessorCheckpointer checkpointer, ShutdownReason reason) {
        logger.info("Shutting down record processor for shard: {}", kinesisShardId);
        // Important to checkpoint after reaching end of shard, so we can start processing data from child shards.
        if (reason == ShutdownReason.TERMINATE) {
            checkpoint(checkpointer);
        }
    }

    /**
     * Checkpoint with retries.
     */
    private void checkpoint(IRecordProcessorCheckpointer checkpointer) {
        logger.info("Checkpointing shard {}", kinesisShardId);
        for (int i = 0; i < NUM_RETRIES; i++) {
            try {
                checkpointer.checkpoint();
                break;
            } catch (ShutdownException se) {
                // Ignore checkpoint if the processor instance has been shutdown (fail over).
                logger.info("Caught shutdown exception, skipping checkpoint. {}", se.getMessage());
                break;
            } catch (ThrottlingException e) {
                // Backoff and re-attempt checkpoint upon transient failures
                if (i >= (NUM_RETRIES - 1)) {
                    logger.error("Checkpoint failed after " + (i + 1) + "attempts.", e);
                    break;
                } else {
                    logger.info("Transient issue when checkpointing - attempt {}  of {}. {}",
                            (i + 1), NUM_RETRIES, e.getMessage());
                }
            } catch (InvalidStateException e) {
                // This indicates an issue with the DynamoDB table (check for table, provisioned IOPS).
                logger.error("Cannot save checkpoint to the DynamoDB table used by the Amazon Kinesis Client Library.", e);
                break;
            }
            try {
                Thread.sleep(BACKOFF_TIME_IN_MILLIS);
            } catch (InterruptedException e) {
                logger.debug("Interrupted sleep {}", e.getMessage());
            }
        }
    }
}
