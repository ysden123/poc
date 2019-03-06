/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.java7.rxjava;

import io.reactivex.*;
import io.reactivex.functions.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yuriy Stul
 */
public class HelloWorld {
    private static Logger logger = LoggerFactory.getLogger(HelloWorld.class);

    public static void main(String[] args) {
        logger.info("==>main");
        Flowable.just("Hello world").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                logger.info(s);
            }
        });
        logger.info("<==main");
    }
}
