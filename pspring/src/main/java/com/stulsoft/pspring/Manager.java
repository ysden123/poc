/*
 * Copyright (c) 2017, William Hill Online. All rights reserved
 */
package com.stulsoft.pspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Yuriy Stul.
 */
@Service
public class Manager {
    @Autowired
    private IService service;

    void userService(){
        System.out.println("service.getNextInt()=" + service.getNextInt());
    }
}
