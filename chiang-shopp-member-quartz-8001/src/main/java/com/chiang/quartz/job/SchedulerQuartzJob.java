package com.chiang.quartz.job;


import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Component;

/**
*/
@Component
public class SchedulerQuartzJob extends SpringBeanJobFactory implements ApplicationContextAware {

    private transient AutowireCapableBeanFactory beanFactory;

    @Override
    public void setApplicationContext(final ApplicationContext context) {
        beanFactory = context.getAutowireCapableBeanFactory();
    }

    @Override
    synchronized protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
        System.out.print("component 任务");
    	final Object job = super.createJobInstance(bundle);
        beanFactory.autowireBean(job);
        return job;
    }

}