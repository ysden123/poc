/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.poc.opencsv.file1;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import com.stulsoft.poc.opencsv.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yuriy Stul
 */
public class File1BeanReader {
    private static final Logger logger = LoggerFactory.getLogger(File1BeanReader.class);

    public static void main(String[] args) {
        logger.info("==>main");
        parse("file1.csv");
        parse("file2.csv");
        parse("file3.csv");
        parse("file4.csv");
    }

    private static void parse(String path) {
        logger.info("Parsing {}", path);
        try {
            var parser = new CSVParserBuilder()
                    .withSeparator('\t')
                    .build();
            var reader = new CSVReaderBuilder(Utils.readerFromResource(path))
                    .withCSVParser(parser)
                    .build();

            var fileBeans = new CsvToBeanBuilder<File1Bean>(reader)
                    .withType(File1Bean.class)
                    .withExceptionHandler(exception -> {
                        logger.debug(exception.toString());
                        logger.error("Error in line number {}, : {}",
                                exception.getLineNumber(),
                                exception.getMessage());
                        return null;
                    })
                    .build()
                    .parse();

            fileBeans.forEach(fileBean -> logger.debug("{}", fileBean));

            reader.close();
        } catch (Exception exception) {
            logger.error(exception.getMessage());
        }
    }
}
