package com.chiang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chiang.feign.TestExceptionServiceFeign;

@RestController
@RequestMapping(value = "/testExceptionFeign")
public class TestExceptionFeignController {

	@Autowired
	private TestExceptionServiceFeign exceptionServiceFeign;

	@RequestMapping(value = "/test1", method = { RequestMethod.GET })
	public String test1() {
		exceptionServiceFeign.testControllerAdvice();
		return "success";
	}
	
	@RequestMapping(value = "/test2", method = { RequestMethod.GET })
	public String test2() {
		return exceptionServiceFeign.testReturn();
	}

}
