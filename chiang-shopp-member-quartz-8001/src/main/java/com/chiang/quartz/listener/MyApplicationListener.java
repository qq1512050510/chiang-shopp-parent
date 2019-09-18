package com.chiang.quartz.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import com.chiang.quartz.config.SchedulerConfig;
import com.chiang.quartz.job.QuartzJob;
import com.chiang.quartz.job.QuartzJob2;
import com.chiang.quartz.service.JobService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@ConditionalOnProperty(value = "quartz.open", havingValue = "true", matchIfMissing = false)
public class MyApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    SchedulerConfig schedulerConfig;
	//@Autowired
    //private SchedulerFactoryBean schedulerConfig;
    @Autowired
	private JobService jobService;
	
    public static AtomicInteger count = new AtomicInteger(0);
    private static String TRIGGER_GROUP_NAME = "test_trriger";
    private static String JOB_GROUP_NAME = "test_job";

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
    	System.out.println("MyApplicationListener");
    	log.info("MyApplicationListener");
        // 防止重复执行 event.getApplicationContext().getParent() == null
        //if ( event.getApplicationContext().getParent() == null&& count.incrementAndGet() <= 1) {
    	if (count.incrementAndGet() <= 1) {
        	log.info("MyApplicationListener");
        	initJob();
        }
    }
    public void initJob() {
    	
    	//jobService.addCronJobExpression(null, null, null, null, QuartzJob.class);
    	String triggerName1 = "trigger1";
    	String triggerGroupName1 = "test_trriger1";
    	String jobName1 = "job1";
    	String jobGroup1 = "test_job1";
    	String triggerName2 = "trigger2";
    	String triggerGroupName2 = "test_trriger2";
    	String jobName2 = "job2";
    	String jobGroup2 = "test_job2";
        CronTrigger trigger = jobService.getConTrigger(triggerName1,triggerGroupName1);
        Map<String,Object> transValue = new HashMap<String,Object>();
        if (null == trigger) {
        	String expression = "0/10 * * * * ?";
        	transValue.put("name", "insert map to job1!");
        	jobService.addCronJobExpression(jobName1, jobGroup1, expression, transValue, QuartzJob.class);
        	log.info("Quartz 创建了job:...:{}", jobName1);
        }else {
        	log.info("job已存在:{}", trigger.getKey());
        }
        CronTrigger trigger2 = jobService.getConTrigger(triggerName2,triggerGroupName2);

        if (null == trigger2) {
        	String expression = "0/15 * * * * ?";
        	transValue.put("name", "insert map to job1!");
        	jobService.addCronJobExpression(jobName2, jobGroup2, expression, transValue, QuartzJob2.class);
        	log.info("Quartz 创建了job2:...:{}", jobName2);
        }else {
        	log.info("job2已存在:{}", trigger.getKey());
        }
    }
}