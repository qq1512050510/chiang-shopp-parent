package com.chiang.quartz.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.chiang.quartz.SchedulerQuartzJob;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
*/
@Configuration
@ConditionalOnProperty(value = "quartz.open", havingValue = "true", matchIfMissing = false)
public class SchedulerQuartzConfig {

    @Autowired
    private SchedulerQuartzJob schedulerQuartzJob;

    @Autowired
    private DataSource dataSourceBean;

    @Bean(name = "schedulerFactoryBean")
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        //获取配置属性
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("quartz.properties"));
        //在quartz.properties中的属性被读取并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();
        //创建SchedulerFactoryBean
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        Properties pro = propertiesFactoryBean.getObject();
        factory.setOverwriteExistingJobs(true);
        factory.setDataSource(dataSourceBean);
        //添加ScheduleName
        factory.setSchedulerName("Cluster_Scheduler");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);

        factory.setTaskExecutor(executor);
        factory.setAutoStartup(true);
        factory.setQuartzProperties(pro);
        factory.setJobFactory(schedulerQuartzJob);
        return factory;
    }
}