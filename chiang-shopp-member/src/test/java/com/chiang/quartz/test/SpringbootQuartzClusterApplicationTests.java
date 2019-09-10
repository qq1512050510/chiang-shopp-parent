package com.chiang.quartz.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.chiang.quartz.service.JobService;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SpringbootQuartzClusterApplicationTests {
	@Autowired
	private JobService jobService;
    @Test
    public void contextLoads() {
    	log.warn("测试开始");
    	//jobService.addAsyncJob("异步任务Title5", "异步任务Content");
    	jobService.runJobTimes();
    	System.out.println("test");
    }
    
    @Test
    public void deleteJob() {
    	log.warn("测试开始");
    	//jobService.addAsyncJob("异步任务Title5", "异步任务Content");
    	jobService.deleteJob("test", "123");
    	System.out.println("test");
    }

}