package com.chiang.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

import com.chiang.api.service.TestExceptionService;

@Component
@FeignClient("member")
public interface TestExceptionServiceFeign extends TestExceptionService {

}
