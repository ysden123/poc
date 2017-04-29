/*
 * Copyright (c) 2016. Yuriy Stul
 */

package com.stulsoft.pspark.basics;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;

/**
 * Demonstrates usage of some RDD operations.
 * <p>
 * Created by Yuriy Stul on 11/21/2016.
 */
public class PRDDJava {
    private static void flatMapTest(final JavaSparkContext sc) {
        System.out.println("==>flatMapTest");
        JavaRDD<String> data1 = sc.parallelize(Arrays.asList("Hello world", "basta"));
        JavaRDD<String> data2 = data1.flatMap(s -> Arrays.asList(s.split(" ")).iterator());

        System.out.println("\ndata1:");
        data1.foreach(i -> System.out.println(i));

        System.out.println("\ndata2:");
        data2.foreach(i -> System.out.println(i));
        System.out.println("<==flatMapTest");
    }

    public static void main(String[] args) {
        System.out.println("==>main");
        SparkConf conf = new SparkConf().setAppName("PRDDJava").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        flatMapTest(sc);

        sc.stop();
        System.out.println("<==main");
    }
}
