/*
 * Copyright (c) 2020. Yuriy Stul
 */

package com.stulsoft.plogging.additivity.level1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Yuriy Stul
 */
public class Level1 {
    private static final Logger logger = LogManager.getLogger(Level1.class);

    public static void test(){
        logger.info("==>test");
        logger.debug("debug");
        logger.info("<==test");
    }
}
