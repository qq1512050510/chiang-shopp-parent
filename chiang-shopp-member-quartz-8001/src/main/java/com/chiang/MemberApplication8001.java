package com.chiang;
/**
 * @author chiang
 *
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;


//@EnableScheduling
@EnableEurekaClient
@SpringBootApplication
public class MemberApplication8001 {
	public static void main(String[] args) {
		SpringApplication.run(MemberApplication8001.class, args);
	}
}