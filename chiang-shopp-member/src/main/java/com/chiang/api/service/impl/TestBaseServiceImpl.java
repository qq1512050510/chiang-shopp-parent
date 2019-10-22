package com.chiang.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chiang.quartz.service.BaseService;

@RestController("/testBaserService")
public class TestBaseServiceImpl {

	@Autowired
	public BaseService baseService;

	@RequestMapping(name = "/testApi", method = {RequestMethod.GET})
	public String testApi() {
		baseService.methdo();
		return baseService.getStorageType();
	}

}
