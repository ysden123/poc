/*
 * Copyright (c) 2018, William Hill Online. All rights reserved
 */
package com.stulsoft.kafkaj.stream.wordcount;

import java.util.Scanner;

/**
 * @author Yuriy Stul.
 */
public class WordCountMain {
    public static void main(String[] args) {
        System.out.println("1 - Producer");
        System.out.println("2 - Processor");
        System.out.println("3 - Reader");
        System.out.println("0 - Exit");
        System.out.println("Enter your choice..");
        Scanner console = new Scanner(System.in);
        String choice = console.next();
        switch (choice) {
            case "0":
                break;
            case "1":
                WordProducer.main(new String[0]);
                break;
            case "2":
                WordCountProcessor.main(new String[0]);
                break;
            case "3":
                WordCountResultReader.main(new String[0]);
                break;
            default:
                System.out.println("Wrong answer");
                break;
        }
    }
}
