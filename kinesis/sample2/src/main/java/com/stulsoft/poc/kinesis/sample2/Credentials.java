/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.poc.kinesis.sample2;

import software.amazon.awssdk.auth.credentials.AwsCredentials;

/**
 * @author Yuriy Stul
 */
public class Credentials implements AwsCredentials {
    @Override
    public String accessKeyId() {
        return AppConfig.awsAccessKeyId();
    }

    @Override
    public String secretAccessKey() {
        return AppConfig.awsSecretAccessKey();
    }
}
