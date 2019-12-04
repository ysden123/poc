/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.poc.kinesis.sample2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.kinesis.lifecycle.events.*;
import software.amazon.kinesis.processor.ShardRecordProcessor;

import java.nio.charset.StandardCharsets;

/**
 * @author Yuriy Stul
 */
public class RecordProcessor implements ShardRecordProcessor {
    private final static Logger logger = LoggerFactory.getLogger(RecordProcessor.class);
    private String shardId;

    @Override
    public void initialize(InitializationInput initializationInput) {
        shardId = initializationInput.shardId();
        logger.info("Initializing Sequence: {}", initializationInput.extendedSequenceNumber());
    }

    @Override
    public void processRecords(ProcessRecordsInput processRecordsInput) {
        logger.info("Processing {} records from shard {}", processRecordsInput.records().size(), shardId);
        var decoder = StandardCharsets.UTF_8.newDecoder();

        processRecordsInput.records().forEach(record -> {
            try {
                var data = decoder.decode(record.data()).toString();
                logger.info("Partition: {}, sequence number = {}, data: {}",
                        record.partitionKey(), record.sequenceNumber(), data);
            } catch (Exception ex) {
                logger.error("Failed getting data from message: " + ex.getMessage());
            }
        });

        try {
            logger.debug("Checkpoint...");
            processRecordsInput.checkpointer().checkpoint();
        }catch(Exception ex){
            logger.error("Failed checkpoint: " + ex.getMessage());
        }
    }

    @Override
    public void leaseLost(LeaseLostInput leaseLostInput) {
        logger.warn("Lost lease, so terminating.");
    }

    @Override
    public void shardEnded(ShardEndedInput shardEndedInput) {
        logger.info("Reached shard end checkpointing.");
        try {
            shardEndedInput.checkpointer().checkpoint();
        } catch (Exception ex) {
            logger.error("Failed checkpointing: " + ex.getMessage());
        }
    }

    @Override
    public void shutdownRequested(ShutdownRequestedInput shutdownRequestedInput) {
        logger.info("Scheduler is shutting down, checkpointing");
        try {
            shutdownRequestedInput.checkpointer().checkpoint();
        } catch (Exception ex) {
            logger.error("Failed checkpointing: " + ex.getMessage());
        }
    }
}
