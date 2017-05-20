/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.pquartz.examples;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

import static org.quartz.DateBuilder.nextGivenSecondDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * This Example will demonstrate how job parameters can be passed into jobs and how state can be maintained
 *
 * @author Yuriy Stul
 * @author Bill Kratzer
 * @see <a href="http://www.quartz-scheduler.org/documentation/quartz-2.x/examples/Example4.html">Quartz Examples: Example 4 - Job Parameters and Job State</a>
 */
public class JobStateExample {

    private void run() throws Exception {

        System.out.println("------- Initializing -------------------");

        // First we must get a reference to a scheduler
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        System.out.println("------- Initialization Complete --------");

        System.out.println("------- Scheduling Jobs ----------------");

        // get a "nice round" time a few seconds in the future....
        Date startTime = nextGivenSecondDate(null, 10);

        // job1 will only run 5 times (at start time, plus 4 repeats), every 10 seconds
        JobDetail job1 = newJob(ColorJob.class).withIdentity("job1", "group1").build();

        SimpleTrigger trigger1 = newTrigger().withIdentity("trigger1", "group1").startAt(startTime)
                .withSchedule(simpleSchedule().withIntervalInSeconds(10).withRepeatCount(4)).build();

        // pass initialization parameters into the job
        job1.getJobDataMap().put(ColorJob.FAVORITE_COLOR, "Green");
        job1.getJobDataMap().put(ColorJob.EXECUTION_COUNT, 1);

        // schedule the job to run
        Date scheduleTime1 = sched.scheduleJob(job1, trigger1);
        System.out.println(job1.getKey() + " will run at: " + scheduleTime1 + " and repeat: " + trigger1.getRepeatCount()
                + " times, every " + trigger1.getRepeatInterval() / 1000 + " seconds");

        // job2 will also run 5 times, every 10 seconds
        JobDetail job2 = newJob(ColorJob.class).withIdentity("job2", "group1").build();

        SimpleTrigger trigger2 = newTrigger().withIdentity("trigger2", "group1").startAt(startTime)
                .withSchedule(simpleSchedule().withIntervalInSeconds(10).withRepeatCount(4)).build();

        // pass initialization parameters into the job
        // this job has a different favorite color!
        job2.getJobDataMap().put(ColorJob.FAVORITE_COLOR, "Red");
        job2.getJobDataMap().put(ColorJob.EXECUTION_COUNT, 1);

        // schedule the job to run
        Date scheduleTime2 = sched.scheduleJob(job2, trigger2);
        System.out.println(job2.getKey().toString() + " will run at: " + scheduleTime2 + " and repeat: " + trigger2.getRepeatCount()
                + " times, every " + trigger2.getRepeatInterval() / 1000 + " seconds");

        System.out.println("------- Starting Scheduler ----------------");

        // All of the jobs have been added to the scheduler, but none of the jobs
        // will run until the scheduler has been started
        sched.start();

        System.out.println("------- Started Scheduler -----------------");

        System.out.println("------- Waiting 60 seconds... -------------");
        try {
            // wait one minute to show jobs
            Thread.sleep(60L * 1000L);
            // executing...
        } catch (Exception e) {
            //
        }

        System.out.println("------- Shutting Down ---------------------");

        sched.shutdown(true);

        System.out.println("------- Shutdown Complete -----------------");

        SchedulerMetaData metaData = sched.getMetaData();
        System.out.println("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");

    }

    public static void main(String[] args) throws Exception {

        JobStateExample example = new JobStateExample();
        example.run();
    }

}
