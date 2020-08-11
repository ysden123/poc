/*
 * Copyright (c) 2020. Yuriy Stul
 */

package com.stulsoft.plogging.additivity;

import com.stulsoft.plogging.additivity.level1.Level1;
import com.stulsoft.plogging.additivity.level1.level2.Level2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Yuriy Stul
 */
public class Application {
    private static final Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("==>main");
        Level1.test();
        Level2.test();
        logger.info("<==main");
    }
}
