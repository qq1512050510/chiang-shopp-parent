package com.chiang.api.service;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chiang.base.ResponseBase;

@RequestMapping("/testRestControllerAdivce")
public interface TestExceptionService {
	
	@RequestMapping(value="/test",method={RequestMethod.GET})
	public void testControllerAdvice();
	
	
	@RequestMapping(value="/testReturn",method={RequestMethod.GET})
	public String testReturn();
}
