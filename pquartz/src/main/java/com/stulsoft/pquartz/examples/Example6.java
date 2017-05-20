/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.pquartz.examples;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author Yuriy Stul
 */
public class Example6 {
    public static void main(String[] args) {
        System.out.println("==>main");
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // define the job and tie it to our MyJob class
            JobDetail jobDetails = newJob(MyJob6.class)
                    .withIdentity("job6", "group1")
                    .usingJobData("stringData", "string value")
                    .usingJobData("retry", true)
                    .usingJobData("timeout", 1000L)
//                    .usingJobData("attempt", 1)
                    .build();
            jobDetails.getJobDataMap().put("attempt",1);

            Trigger trigger = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?")) // Every 10 seconds (0, 10, ...)
                    .build();

            scheduler.scheduleJob(jobDetails, trigger);

            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        System.out.println("<==main");
    }
}
