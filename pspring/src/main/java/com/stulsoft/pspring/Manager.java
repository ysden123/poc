/*
 * Copyright (c) 2017, William Hill Online. All rights reserved
 */
package com.stulsoft.pspring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Yuriy Stul.
 */
@Service
public class Manager {
    static Logger logger = LoggerFactory.getLogger(Manager.class);
    @Autowired
    private IService service;

    void userService(){
        logger.debug("==>userService");
        logger.debug("service.getNextInt()=" + service.getNextInt());
        logger.debug("<==userService");
    }
}
