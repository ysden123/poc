/*
 * Copyright (c) 2017, Yuriy Stul. All rights reserved
 */
package com.stulsoft.pspring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Yuriy Stul.
 */
@Component
public class Manager3 {
    static Logger logger = LoggerFactory.getLogger(Manager3.class);
    @Autowired
    private IService3 service;

    void userService(){
        logger.debug("==>userService");
        logger.debug("service.getNextInt()=" + service.getNextInt());
        logger.debug("<==userService");
    }
}
