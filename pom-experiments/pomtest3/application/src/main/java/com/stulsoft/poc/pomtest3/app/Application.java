/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.pomtest3.app;

import com.stulsoft.poc.pomtest3.common.Utils;

/**
 * @author Yuriy Stul
 */
public class Application {
    public static void main(String[] args) {
        System.out.println("==>main");
        System.out.printf("foo=%s%n", Utils.foo());
        System.out.println("<==main");
    }
}
