package com.chiang.quartz.job;

import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.quartz.QuartzJobBean;

import lombok.extern.slf4j.Slf4j;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Slf4j
public class QuartzJob2 extends test {
	
	public String name ="submijob";
	public String expression ="0/5 ****";
	
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        String taskName = context.getJobDetail().getJobDataMap().getString("name");
        log.info("---> Quartz job 2 {}, {} <----", new Date(), taskName);
    }
}
