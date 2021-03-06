package com.chiang.quartz.service.impl;

import com.chiang.quartz.constant.QuartzConstant;
import com.chiang.quartz.constant.SystemConstantImpl;
import com.chiang.quartz.job.AsyncJob;
import com.chiang.quartz.job.QuartzJob;
import com.chiang.quartz.job.test;
import com.chiang.quartz.service.JobService;

import lombok.extern.slf4j.Slf4j;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.UnknownFormatConversionException;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
@Service
@Slf4j
public class JobServiceImpl implements JobService {

    //@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    SystemConstantImpl systemConstant = SystemConstantImpl.initObject();

    /**
     * 定时触发的功能接口
     *
     * @param jobName  工作名称
     * @param jobGroup 工作组
     * @param date     触发时间
     * @param transValue 传递的参数
     */
    @Override
    public void addCronJob(String jobName, String jobGroup, Date date, Map<String, Object> transValue, Class clazz) {
        System.out.println("innerDev===========");
        if (ObjectUtils.isEmpty(date)) {
            throw new UnknownFormatConversionException(
                    (String) systemConstant.fetchAttrValue(QuartzConstant.class.getSimpleName(),
                            QuartzConstant.QUARTZ_DATE_NOT_NULL));
        }
        if(ObjectUtils.isEmpty(jobName) || ObjectUtils.isEmpty(jobGroup)) {
            throw new UnknownFormatConversionException(
                    (String) systemConstant.fetchAttrValue(QuartzConstant.class.getSimpleName(),
                            QuartzConstant.QUARTZ_DATE_NOT_NULL));
        }
        // 参数不能为空
        try {
            String dateCron = new SimpleDateFormat("ss mm HH dd MM ? yyyy").format(date);
            Scheduler scheduler = schedulerFactoryBean.getScheduler();

            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail == null) {
                //构建job信息
                jobDetail = JobBuilder.newJob(clazz).withIdentity(jobName, jobGroup).build();
                //用JobDataMap来传递数据
                jobDetail.getJobDataMap().put("transValue", transValue);

                //表达式调度构建器(即任务执行的时间)
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(dateCron);
                //CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronScheduleNonvalidatedExpression("*/5 * * * * ?");

                //按新的cronExpression表达式构建一个新的trigger
                CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName + "_trigger", jobGroup + "_trigger")
                        .withSchedule(scheduleBuilder).build();
                scheduler.scheduleJob(jobDetail, trigger);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addAsyncJob(String jobName, String jobGroup) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();

            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail != null) {
                System.out.println("job:" + jobName + " 已存在");
            } else {
                //构建job信息,在用JobBuilder创建JobDetail的时候，有一个storeDurably()方法，可以在没有触发器指向任务的时候，将任务保存在队列中了。然后就能手动触发了
                jobDetail = JobBuilder.newJob(AsyncJob.class).withIdentity(jobName, jobGroup).storeDurably().build();
                jobDetail.getJobDataMap().put("asyncData", "this is a async task");
                Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName + "_trigger", jobGroup + "_trigger") //定义name/group
                        .startNow()//一旦加入scheduler，立即生效
                        .withSchedule(simpleSchedule())//使用SimpleTrigger
                        .build();
                scheduler.scheduleJob(jobDetail, trigger);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pauseJob(String jobName, String jobGroup) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName + "_trigger", jobGroup + "_trigger");

            scheduler.pauseTrigger(triggerKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 恢复任务
     *
     * @param jobName
     * @param jobGroup
     */
    @Override
    public void resumeJob(String jobName, String jobGroup) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName + "_trigger", jobGroup + "_trigger");
            scheduler.resumeTrigger(triggerKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int deleteJob(String jobName, String jobGroup) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }
    
    
    /**
     * 定时触发的功能接口
     *
     * @param jobName  工作名称
     * @param jobGroup 工作组
     * @param expression 触发表达式
     * @param transValue 传递的参数
     */
    @Override
    public void addCronJobExpression(String jobName, String jobGroup, String expression, Map<String, Object> transValue, Class clazz) {
        System.out.println("innerDev===========");
        
        if (ObjectUtils.isEmpty(expression)) {
            throw new UnknownFormatConversionException(
                    (String) systemConstant.fetchAttrValue(QuartzConstant.class.getSimpleName(),
                            QuartzConstant.QUARTZ_DATE_NOT_NULL));
        }
        if(ObjectUtils.isEmpty(jobName) || ObjectUtils.isEmpty(jobGroup)) {
            throw new UnknownFormatConversionException(
                    (String) systemConstant.fetchAttrValue(QuartzConstant.class.getSimpleName(),
                            QuartzConstant.QUARTZ_DATE_NOT_NULL));
        }
        // 参数不能为空
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();

            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail == null) {
                //构建job信息
                jobDetail = JobBuilder.newJob(clazz).withIdentity(jobName, jobGroup).build();
                //用JopDataMap来传递数据
                jobDetail.getJobDataMap().put("transValue", transValue);
               
                //表达式调度构建器(即任务执行的时间) 
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(expression);
                //CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronScheduleNonvalidatedExpression("*/5 * * * * ?");

                //按新的cronExpression表达式构建一个新的trigger
                CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName + "_trigger", jobGroup + "_trigger")
                        .withSchedule(scheduleBuilder).build();
                scheduler.scheduleJob(jobDetail, trigger);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@Override
	public CronTrigger getConTrigger(String triggerName, String triggerGroupName) {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
		try {
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			return trigger;
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void addCronJobExpression(String jobName, String jobGroup, String expression, Map<String, Object> transValue,
			test clazz) {
		log.info(clazz.name);
		clazz.getClass();
	}
	
	
	
}