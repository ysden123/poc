package com.stulsoft.pgradle;

import org.joda.time.Period;

public class Test1 {
    public static void main(String[] args) {
        System.out.println("==>main");

        Period period = new Period().withDays(2).withHours(5);

        System.out.printf("period: %s%n", period);
    }
}
