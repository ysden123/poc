/*
 * Copyright (c) 2021. StulSoft
 */

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.stulsoft.poc.pgradle.app;

import com.stulsoft.poc.pgradle.list.LinkedList;

import static com.stulsoft.poc.pgradle.app.StringUtils.join;
import static com.stulsoft.poc.pgradle.app.StringUtils.split;
import static com.stulsoft.poc.pgradle.app.MessageUtils.getMessage;

public class App {
    public static void main(String[] args) {
        LinkedList tokens;
        tokens = split(getMessage());
        System.out.println(join(tokens));
    }
}
