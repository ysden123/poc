/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.read.resource;

import java.io.*;

/**
 * @author Yuriy Stul
 */
public class ReaderMain {
    public static void main(String[] args) {
        System.out.println("==>main");
        ReaderMain readerMain = new ReaderMain();
        readerMain.readResource();
        readerMain.readFile();
        System.out.println("<==main");
    }

    private void readResource() {
        System.out.println("==>readResource");

        try {
            String data = readFromInputStream(getClass().getClassLoader().getResourceAsStream("data.txt"));
            System.out.println("data=" + data);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("<==readResource");
    }

    private void readFile() {
        System.out.println("==>readFile");
        try (FileInputStream inputStream = new FileInputStream("data2.txt")) {
            String data2 = readFromInputStream(inputStream);
            System.out.println("data2=" + data2);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("<==readFile");
    }

    private String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}
