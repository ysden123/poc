/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.poc.kinesis.sample1;

import java.io.File;
import java.util.*;

/**
 * Looks for duplicated messages with same Sequence Number
 *
 * @author Yuriy Stul
 */
public class LogAnalyzer {
    public static void main(String[] args) {
        var textToSearch = "getSequenceNumber = ";
        try {
            var scanner = new Scanner(new File("log/sample1.log"));
            var map = new HashMap<String, Long>();
            while (scanner.hasNext()) {
                var line = scanner.nextLine();
                if (line.contains(textToSearch)) {
                    var startPosition = line.indexOf(textToSearch) + textToSearch.length();
//                    var endPosition = line.indexOf(System.lineSeparator(), startPosition);
                    var endPosition = line.length();
                    var seqNumber = line.substring(startPosition, endPosition);
                    var prevValue = map.computeIfAbsent(seqNumber, k -> 0L);
                    map.put(seqNumber, prevValue + 1L);
                }
            }
            scanner.close();
            System.out.printf("Looking for duplicates for %d records ...%n", map.size());
            var found = false;
            for (Map.Entry<String, Long> entry : map.entrySet()) {
                if (entry.getValue() > 1) {
                    System.out.printf("Found duplicates for %s, number = %d%n", entry.getKey(), entry.getValue());
                    found = true;
                }
            }
            if (!found)
                System.out.println("Found no duplicates.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
