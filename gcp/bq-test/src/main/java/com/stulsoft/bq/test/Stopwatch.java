/*
   Created by Yuriy Stul 2018
*/
package com.stulsoft.bq.test;

import java.util.concurrent.TimeUnit;

/**
 * @author Yuriy Stul
 */
public class Stopwatch {
    private long start;
    private long end;

    public Stopwatch() {
        start();
    }

    public void start() {
        start = System.nanoTime();
        end = 0L;
    }

    public void stop() {
        end = System.nanoTime();
    }

    public long duration() {
        if (end == 0l)
            stop();
        return TimeUnit.MILLISECONDS.convert(end - start, TimeUnit.NANOSECONDS);
    }
}
