/*
 * Copyright (c) 2017, William Hill Online. All rights reserved
 */
package com.stulsoft.pspring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author Yuriy Stul.
 */
@Component
public class Manager2 {
    static Logger logger = LoggerFactory.getLogger(Manager2.class);
    @Autowired
    private IService2 service;

    void userService(){
        logger.debug("==>userService");
        logger.debug("service.getNextInt()=" + service.getNextInt());
        logger.debug("<==userService");
    }
}
