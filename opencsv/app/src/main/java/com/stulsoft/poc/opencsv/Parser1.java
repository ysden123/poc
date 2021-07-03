/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.poc.opencsv;

import com.opencsv.CSVIterator;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yuriy Stul
 */
public class Parser1 {
    private static final Logger logger = LoggerFactory.getLogger(Parser1.class);

    public static void main(String[] args) {
        logger.info("==>main");

        test1();
        test2();
        test3();
    }

    private static void test1() {
        logger.info("==>test1");
        try {
            var parser = new CSVParserBuilder()
                    .withSeparator('\t')
                    .build();
            var reader = new CSVReaderBuilder(Utils.readerFromResource("file1.csv"))
                    .withCSVParser(parser)
                    .build();
            reader.readAll().forEach(columns -> logger.debug(String.join("|", columns)));
            reader.close();
        } catch (Exception exception) {
            logger.error(exception.getMessage(), exception);
        }
    }

    private static void test2() {
        logger.info("==>test2");
        try {
            var parser = new CSVParserBuilder()
                    .withSeparator('\t')
                    .build();
            var reader = new CSVReaderBuilder(Utils.readerFromResource("file1.csv"))
                    .withCSVParser(parser)
                    .build();

            var iterator = reader.iterator();

            while (iterator.hasNext()) {
                var columns = iterator.next();

                if (iterator.hasNext())
                    logger.debug(String.join("|", columns));
                else
                    logger.debug(String.join("|", columns) + " the last line");
            }
            reader.close();
        } catch (Exception exception) {
            logger.error(exception.getMessage(), exception);
        }
    }

    private static void test3() {
        logger.info("==>test3");
        try {
            var parser = new CSVParserBuilder()
                    .withSeparator('\t')
                    .build();
            var reader = new CSVReaderBuilder(Utils.readerFromResource("file1.csv"))
                    .withCSVParser(parser)
                    .build();
            var iterator = new CSVIterator(reader);

            while (iterator.hasNext()) {
                var columns = iterator.next();

                if (iterator.hasNext())
                    logger.debug(String.join("|", columns));
                else
                    logger.debug(String.join("|", columns) + " the last line");
            }
            reader.close();
        } catch (Exception exception) {
            logger.error(exception.getMessage(), exception);
        }
    }
}
