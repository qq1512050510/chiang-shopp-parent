package com.chiang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PCWebApp {

	public static void main(String[] args) {
		SpringApplication.run(PCWebApp.class, args);
	}

}
