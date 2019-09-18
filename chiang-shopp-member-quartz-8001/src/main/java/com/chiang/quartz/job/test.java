package com.chiang.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public abstract class test extends QuartzJobBean {
	public  String name ="submijob";
	public String expression ="0/5 ****";
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
	}
}
