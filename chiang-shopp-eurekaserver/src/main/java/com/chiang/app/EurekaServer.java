package com.chiang.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.util.ClassUtils;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServer {

	public static void main(String[] args) {
		System.out.print(ClassUtils.getDefaultClassLoader().getResource("").getPath());
		SpringApplication.run(EurekaServer.class, args);
	}

}
