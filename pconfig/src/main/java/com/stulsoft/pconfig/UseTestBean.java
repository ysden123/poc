package com.stulsoft.pconfig;

import com.typesafe.config.ConfigBeanFactory;
import com.typesafe.config.ConfigFactory;

/**
 * @author Yuriy Stul
 */
public class UseTestBean {
    public static void main(String[] args) {
        System.out.println("==>main");
        com.typesafe.config.Config c = ConfigFactory.load("array.conf");
        c.getConfig("test")
                .getConfigList("testList")
                .forEach(config -> {
                    TestBean tb = ConfigBeanFactory.create(config, TestBean.class);
                    System.out.println(tb);
                    System.out.printf("p1 = %d, p2: %s\n", tb.getP1(),tb.getP2());
                });
        System.out.println("<==main");
    }
}
