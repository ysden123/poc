/*
 * Copyright (c) 2018, William Hill Online. All rights reserved
 */
package com.stulsoft.pspring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Yuriy Stul.
 */
public class Main2 {
    private static Logger logger = LoggerFactory.getLogger(Main2.class);
    public static void main(String[] args){
        logger.info("==>main");
        ApplicationContext applicationContext = null;
        try{
            logger.info("Getting application context");
            applicationContext = new ClassPathXmlApplicationContext("beans1.xml");
            logger.info("Application context is ready");
            Manager3 manager3 = applicationContext.getBean(Manager3.class);
            manager3.userService();
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        finally {
            if (applicationContext != null)
                ((AbstractApplicationContext)applicationContext).close();
        }
        logger.info("==<main");
    }
}
