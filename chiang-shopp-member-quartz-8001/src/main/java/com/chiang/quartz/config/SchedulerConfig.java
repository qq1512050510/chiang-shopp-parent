package com.chiang.quartz.config;


import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executor;


/**
 * Schedule:调度器
 * Trigger：触发器
 * job：任务（一个任务可以对应多个触发器）
 */
@Configuration
@ConditionalOnProperty(value = "quartz.open", havingValue = "true", matchIfMissing = false)
public class SchedulerConfig {

    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private JobFactory jobFactory;

    /**
     * 调度器
     *
     * @return
     * @throws Exception
     */
    @Bean
    public Scheduler scheduler() throws Exception {
        Scheduler scheduler = schedulerFactoryBean().getScheduler();
        return scheduler;
    }

    /**
     * Scheduler工厂类
     *
     * @return
     * @throws IOException
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setSchedulerName("Cluster_Scheduler");
        //
        //factory.setOverwriteExistingJobs(true);
        factory.setDataSource(dataSource);
        factory.setApplicationContextSchedulerContextKey("applicationContext");
        factory.setTaskExecutor(schedulerThreadPool());
        factory.setQuartzProperties(quartzProperties());
        //将quartz的job交给Spring容器去管理
        factory.setJobFactory(jobFactory);
        factory.setStartupDelay(10);//延迟10s执行
        return factory;
    }

    /**
     * 加载配置属性
     *
     * @return
     * @throws IOException
     */
    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/spring-quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    /**
     * schedule配置线程池
     *
     * @return
     */
    @Bean
    public Executor schedulerThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        System.out.println("Runtime.getRuntime().availableProcessors():"+Runtime.getRuntime().availableProcessors());
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors());
        executor.setQueueCapacity(Runtime.getRuntime().availableProcessors());
        return executor;
    }
}

