/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.poc.pgradle;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
    }
}
