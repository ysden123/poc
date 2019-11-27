/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.poc.kinesis.sample1;

import com.amazonaws.auth.AWSCredentials;

/**
 * @author Yuriy Stul
 */
public class Credentials implements AWSCredentials {
    @Override
    public String getAWSAccessKeyId() {
        return AppConfig.awsAccessKeyId();
    }

    @Override
    public String getAWSSecretKey() {
        return AppConfig.awsSecretAccessKey();
    }
}
