package com.chiang.api.service.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.chiang.api.service.TestExceptionService;
import com.chiang.exception.UnauthenticatedException;
/**
 * 测试 Exception
 * @author adp
 *
 */
@RestController
public class TestRestControllerAdviceImpl implements TestExceptionService {

	@Override
	public void testControllerAdvice() {
		throw new UnauthenticatedException("test ControllerAdvice");
	}

	@Override
	public String testReturn() {
		return "tetReturn";
	}
	
}
