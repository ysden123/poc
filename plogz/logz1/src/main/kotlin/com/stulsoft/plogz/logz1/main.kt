/*
 * Copyright (c) 2020. Yuriy Stul
 */

package com.stulsoft.plogz.logz1

import org.slf4j.LoggerFactory

/**
 * @author Yuriy Stul
 */
private val logger = LoggerFactory.getLogger("")
fun main() {
    println("start")
    logger.info("start")
    logger.debug("Using token from System Environment")
    logger.error("Test error")
}