/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.poc.kinesis.sample2;

import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;

/**
 * @author Yuriy Stul
 */
public class CredentialsProvider implements AwsCredentialsProvider {
    @Override
    public AwsCredentials resolveCredentials() {
        return new Credentials();
    }
}
