package com.chiang.quartz.listener;

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

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@ConditionalOnProperty(value = "quartz.open", havingValue = "true", matchIfMissing = false)
public class StartApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
   // @Autowired
   // SchedulerConfig schedulerConfig;
	@Autowired
    private SchedulerFactoryBean schedulerConfig;
	
    public static AtomicInteger count = new AtomicInteger(0);
    private static String TRIGGER_GROUP_NAME = "test_trriger";
    private static String JOB_GROUP_NAME = "test_job";

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
    	System.out.println("tttttttttttttt123");
    	log.warn("tttttttttttt");
        // 防止重复执行 event.getApplicationContext().getParent() == null
        //if ( event.getApplicationContext().getParent() == null&& count.incrementAndGet() <= 1) {
    	if (count.incrementAndGet() <= 1) {
        	log.warn("AAAAAAAAAA");
        	initMyJob();
        }
    }

    public void initMyJob() {
        Scheduler scheduler = null;
        try {
            //scheduler = schedulerConfig.scheduler();
        	scheduler = schedulerConfig.getScheduler();

            TriggerKey triggerKey = TriggerKey.triggerKey("trigger1", TRIGGER_GROUP_NAME);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (null == trigger) {
                Class clazz = QuartzJob.class;
                JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity("job1", JOB_GROUP_NAME).build();
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ?");
                trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", TRIGGER_GROUP_NAME)
                        .withSchedule(scheduleBuilder).build();
                scheduler.scheduleJob(jobDetail, trigger);
                log.info("Quartz 创建了job:...:{}", jobDetail.getKey());
            } else {
                log.info("job已存在:{}", trigger.getKey());
            }

            TriggerKey triggerKey2 = TriggerKey.triggerKey("trigger2", TRIGGER_GROUP_NAME);
            CronTrigger trigger2 = (CronTrigger) scheduler.getTrigger(triggerKey2);
            if (null == trigger2) {
                Class clazz = QuartzJob2.class;
                JobDetail jobDetail2 = JobBuilder.newJob(clazz).withIdentity("job2", JOB_GROUP_NAME).build();
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/15 * * * * ?");
                trigger2 = TriggerBuilder.newTrigger().withIdentity("trigger2", TRIGGER_GROUP_NAME)
                        .withSchedule(scheduleBuilder).build();
                scheduler.scheduleJob(jobDetail2, trigger2);
                log.info("Quartz 创建了job:...:{}", jobDetail2.getKey());
            } else {
                log.info("job已存在:{}", trigger2.getKey());
            }
            scheduler.start();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }
}