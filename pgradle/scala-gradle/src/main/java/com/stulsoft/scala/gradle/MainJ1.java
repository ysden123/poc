/*
 * Copyright (c) 2018, William Hill Online. All rights reserved
 */
package com.stulsoft.scala.gradle;

/**
 * @author Yuriy Stul.
 */
public class MainJ1 {
    public static void main(String[] args) {
        System.out.println("Hello from MainJ1");
        ScalaClass1 sc1 = new ScalaClass1("test", 21);
        System.out.format("name with age is %s%n", sc1.nameWithAge());
    }
}
