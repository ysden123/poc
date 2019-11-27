/*
 * Copyright (c) 2019. Webpals
 */

package com.stulsoft.poc.kinesis.sample1;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;

/**
 * @author Yuriy Stul
 * @since 1.0.0
 */
public class AppConfig {
    private static final AppConfig instance = new AppConfig();
    private static final Config config;

    static {
        config = ConfigFactory
                .parseFile(new File("application.conf"))
                .withFallback(ConfigFactory.load());
    }

    private AppConfig() {
    }

    public static AppConfig getInstance() {
        return instance;
    }

    public static String awsAccessKeyId() {
        return config.getConfig("aws").getString("aws_access_key_id");
    }

    public static String awsSecretAccessKey() {
        return config.getConfig("aws").getString("aws_secret_access_key");
    }

    public static String awsRegion() {
        return config.getConfig("aws").getString("aws_region");
    }

    public static String appName() {
        return config.getConfig("app").getString("name");
    }

    public static String appDescription() {
        return config.getConfig("app").getString("description");
    }

    public static String streamName() {
        return config.getConfig("app").getString("streamName");
    }

    public static Integer streamSize() {
        return config.getConfig("app").getInt("streamSize");
    }
}
