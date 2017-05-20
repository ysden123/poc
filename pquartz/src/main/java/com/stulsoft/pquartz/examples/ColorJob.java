/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.pquartz.examples;

import org.quartz.*;

import java.util.Date;

/**
 * This is just a simple job that receives parameters and maintains state
 *
 * @author Yuriy Stul
 * @author Bill Kratzer
 * @see <a href="http://www.quartz-scheduler.org/documentation/quartz-2.x/examples/Example4.html">Quartz Examples: Example 4 - Job Parameters and Job State</a>
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ColorJob implements Job {

    // parameter names specific to this job
    static final String FAVORITE_COLOR = "favorite color";
    static final String EXECUTION_COUNT = "count";

    // Since Quartz will re-instantiate a class every time it
    // gets executed, members non-static member variables can
    // not be used to maintain state!
    private int _counter = 1;

    /**
     * <p>
     * Empty constructor for job initialization
     * </p>
     * <p>
     * Quartz requires a public empty constructor so that the
     * scheduler can instantiate the class whenever it needs.
     * </p>
     */
    public ColorJob() {
    }

    /**
     * <p>
     * Called by the <code>{@link org.quartz.Scheduler}</code> when a
     * <code>{@link org.quartz.Trigger}</code> fires that is associated with
     * the <code>Job</code>.
     * </p>
     *
     * @throws JobExecutionException if there is an exception while executing the job.
     */
    public void execute(JobExecutionContext context)
            throws JobExecutionException {

        // This job simply prints out its job name and the
        // date and time that it is running
        JobKey jobKey = context.getJobDetail().getKey();

        // Grab and print passed parameters
        JobDataMap data = context.getJobDetail().getJobDataMap();
        String favoriteColor = data.getString(FAVORITE_COLOR);
        int count = data.getInt(EXECUTION_COUNT);
        System.out.println("ColorJob: " + jobKey + " executing at " + new Date() + "\n" +
                "  favorite color is " + favoriteColor + "\n" +
                "  execution count (from job map) is " + count + "\n" +
                "  execution count (from job member variable) is " + _counter);

        // increment the count and store it back into the 
        // job map so that job state can be properly maintained
        count++;
        data.put(EXECUTION_COUNT, count);

        // Increment the local member variable 
        // This serves no real purpose since job state can not 
        // be maintained via member variables!
        _counter++;
    }

}

