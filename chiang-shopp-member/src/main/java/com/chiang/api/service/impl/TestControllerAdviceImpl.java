package com.chiang.api.service.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chiang.exception.UnauthenticatedException;

@Controller
@RequestMapping("/testControllerAdivce")
public class TestControllerAdviceImpl {

	@RequestMapping(value="/test",method={RequestMethod.GET})
	public void testControllerAdvice() {
		throw new UnauthenticatedException("test ControllerAdvice");
	}
	
}
