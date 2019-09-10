package com.chiang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEurekaClient
//定时任务
//@EnableScheduling
public class MemberServer {
	
	public static void main(String[] args) {
		SpringApplication.run(MemberServer.class, args);
	}

}
