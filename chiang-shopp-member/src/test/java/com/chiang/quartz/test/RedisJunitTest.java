package com.chiang.quartz.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.chiang.base.BaseRedisService;
import com.chiang.quartz.service.JobService;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RedisJunitTest {
	@Autowired
	private BaseRedisService redisService;
    @Test
    public void contextLoads() {
    	log.warn("测试开始");
    	redisService.delKey("");
    	redisService.setString("TOKEN_MEMBER-7abbd39f-9ffe-413b-aff0-af2d2aba4f15", "50", new Long(50000));
    	redisService.setString("TOKEN_MEMBER-7abbd39f-9ffe-413b-aff0-af2d2aba4e15", "50", null);
    }
    @Test
    public void deleteRedis() {
    	log.warn("测试开始");
    	redisService.delKey("key1");
    }
    
    @Test
    public void getKey() {
    	log.warn("测试开始");
    	log.info((String) redisService.getString("TOKEN_MEMBER-7abbd39f-9ffe-413b-aff0-af2d2aba4e15"));
    	log.info((String) redisService.getString("TOKEN_MEMBER-7abbd39f-9ffe-413b-aff0-af2d2aba4f15"));
    }
    

}