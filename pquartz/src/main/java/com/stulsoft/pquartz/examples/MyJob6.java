/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.pquartz.examples;

import org.quartz.*;

/**
 * @author Yuriy Stul
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class MyJob6 implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Executing MyJob6");

        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        int attempt = jobDataMap.getInt("attempt");
        System.out.printf("stringData: %s, retry is %s, timeout = %d, attempt = %d\n",
                jobDataMap.getString("stringData"),
                jobDataMap.getBoolean("retry"),
                jobDataMap.getLong("timeout"),
                attempt
        );

        ++attempt;
        jobDataMap.put("attempt", attempt);
    }
}
