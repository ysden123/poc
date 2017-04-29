/*
 * Copyright (c) 2016. Yuriy Stul
 */

package com.stulsoft.pspark.basics;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.DoubleFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * Simple Spark application.
 * <p>
 * From the book Nick Pentreath "Machine Learning with Spark"
 * <p>
 * Created by Yuriy Stul on 11/15/2016.
 */
public class SimpleSparkJavaApp {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("Simple Spark App").setMaster("local[2]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        // we take the raw data in CSV format and convert it into a
        // set of records of the form (user, product, price)
        JavaRDD<String[]> data = null;

        try {
            data = sc.textFile(new File(SimpleSparkJavaApp.class.getClassLoader().getResource("data/UserPurchaseHistory.csv").toURI()).getAbsolutePath())
                    .map(new Function<String, String[]>() {
                        @Override
                        public String[] call(String s) throws Exception {
                            return s.split(",");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        // let's count the number of purchases
        long numPurchases = data.count();
        // let's count how many unique users made purchases
        long uniqueUsers = data.map(new Function<String[], String>() {
            @Override
            public String call(String[] strings) throws Exception {
                return strings[0];
            }
        }).distinct().count();

        // let's sum up our total revenue
        double totalRevenue = data.mapToDouble(new DoubleFunction<String[]>() {
            @Override
            public double call(String[] strings) throws Exception {
                return Double.parseDouble(strings[2]);
            }
        }).sum();

        // let's find our most popular product
        // first we map the data to records of (product, 1)
        // using a PairFunction and the Tuple2 class.
        // then we call a reduceByKey operation with a Function2,
        // which is essentially the sum function
        List<Tuple2<String, Integer>> pairsUnmodified = data.mapToPair(new PairFunction<String[], String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String[] strings) throws Exception {
                return new Tuple2<String, Integer>(strings[1], 1);
            }
        }).reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        }).collect();

        // finally we sort the result. Note we need to create a Comparator function,
        // that reverses the sort order.
        List<Tuple2<String, Integer>> pairs = new ArrayList<>(pairsUnmodified);
        pairs.sort(new Comparator<Tuple2<String, Integer>>() {
            @Override
            public int compare(Tuple2<String, Integer> o1, Tuple2<String, Integer> o2) {
                return -(o1._2() - o2._2());
            }
        });
        String mostPopular = pairs.get(0)._1();
        int purchases = pairs.get(0)._2();
        System.out.println("Total purchases: " + numPurchases);
        System.out.println("Unique users: " + uniqueUsers);
        System.out.println("Total revenue: " + totalRevenue);
        System.out.println(String.format("Most popular product: %s with %d purchases", mostPopular, purchases));
    }
}
