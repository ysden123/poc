/*
   Created by Yuriy Stul 2018
*/
package com.stulsoft.bq.test;

import java.util.concurrent.TimeUnit;

/**
 * @author Yuriy Stul
 */
public class Duration {
    private long start;
    private long end;

    public void start() {
        start = System.nanoTime();
        end = start;
    }

    public void stop() {
        end = System.nanoTime();
    }

    public long duration() {
        return TimeUnit.MILLISECONDS.convert(end - start, TimeUnit.NANOSECONDS);
    }
}
