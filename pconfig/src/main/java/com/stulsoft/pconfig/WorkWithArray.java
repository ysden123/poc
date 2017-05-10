package com.stulsoft.pconfig;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * @author Yuriy Stul
 */
public class WorkWithArray {
    public static void main(String[] args) {
        System.out.println("==>main");
        Config c = ConfigFactory.load("array.conf");
        c.getConfig("test")
                .getConfigList("testList")
                .forEach(config -> System.out.printf("p1 = %d, p2: %s\n", config.getInt("p1"), config.getString("p2")));
        System.out.println("<==main");
    }
}
