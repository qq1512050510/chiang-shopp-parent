package com.chiang.quartz.job;

import java.util.Date;
import java.util.Map;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.quartz.QuartzJobBean;

import lombok.extern.slf4j.Slf4j;

//持久化
@PersistJobDataAfterExecution
//禁止并发执行(Quartz不要并发地执行同一个job定义（这里指一个job类的多个实例）)
@DisallowConcurrentExecution
@Slf4j
public class QuartzJob extends QuartzJobBean {
	
	public static String name ="submijob";
	public String expression ="0/5 ****";
	//
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        Map<String,Object> taskName = context.getJobDetail().getJobDataMap().getWrappedMap();
        log.info("---> Quartz job {}, {} <----", new Date(), taskName);
    }
}