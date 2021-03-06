/*
   Created by Yuriy Stul 2018
*/
package com.stulsoft.gcp.datastore;

import java.util.concurrent.TimeUnit;

/**
 * @author Yuriy Stul
 */
public class Duration {
    private long start;
    private long end;

    public Duration() {
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
        if (end == 0L)
            end = System.nanoTime();
        return TimeUnit.MILLISECONDS.convert(end - start, TimeUnit.NANOSECONDS);
    }
}
