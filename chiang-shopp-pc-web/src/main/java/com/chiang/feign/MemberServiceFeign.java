package com.chiang.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

import com.chiang.api.service.MemberService;
/*
 * 
 * FeignClient配置与chiang-shopp-member application.yml中的spring.application.name配置一直
 * spring:
	  application:
	    name: member*/
@Component
@FeignClient("member")
public interface MemberServiceFeign extends MemberService{

}
