/*
 * Copyright (c) 2016. Yuriy Stul
 */

package com.stulsoft.pkafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;

/**
 * Check connection.
 *
 * @author Yuriy Stul
 */
public class CheckConnection {
    private static final Logger logger = LoggerFactory.getLogger(CheckConnection.class);

    public static void main(String[] args) {
        logger.debug("==>CheckConnection");
        logger.debug("Is Kafka server available: {}", checkConnection());
        logger.debug("<==CheckConnection");
    }

    static boolean checkConnection() {
        boolean available = false;
        final String urlString = "http://localhost:9092";
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            logger.debug("responseCode={}", responseCode);
        } catch (ConnectException e) {
            logger.error("No connection with Kafka server. Error: {}", e.getMessage());
        } catch (SocketException e) {
            logger.info("Kafka server is available");
            available = true;
        } catch (Exception e) {
            logger.error("Failure", e);
        }
        return available;
    }
}
