package com.stulsoft.bq.test;

import java.util.logging.Logger;

/**
 * @author Yuriy Stul
 * @since 3/4/2018
 */
public class LoggerUtils {
    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tF %1$tT %4$s %2$s %5$s%6$s%n");
    }

    /**
     * Returns a logger for specified name.
     *
     * @param name the logger's name.
     * @return the logger for specified name.
     */
    public static Logger getLogger(String name) {
        return Logger.getLogger(name);
    }
}
