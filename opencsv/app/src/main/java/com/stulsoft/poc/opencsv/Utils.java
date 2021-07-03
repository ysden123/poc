/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.poc.opencsv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author Yuriy Stul
 */
public class Utils {
    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    public static Reader readerFromResource(String path) {
        try {
            var inputStream = Utils.class.getClassLoader().getResourceAsStream(path);
            return new InputStreamReader((inputStream));
        }catch(Exception exception){
            logger.error(exception.getMessage(), exception);
            throw exception;
        }
    }

}
