package com.chiang.quartz.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by xiaozhi on 2019/2/25.
 */
public class QuartzJob implements Job {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
	@Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(simpleDateFormat.format(new Date()));
        System.out.println("#################QuartzJob");
    }
}
