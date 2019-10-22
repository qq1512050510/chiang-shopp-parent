package com.chiang.quartz.service.impl;

import org.springframework.stereotype.Service;

import com.chiang.constant.Constants;
import com.chiang.quartz.service.BaseService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(Constants.BASE_SERVICE1)
public class BaseService1Impl implements BaseService {
	private String type = Constants.BASE_SERVICE1;

	@Override
	public void methdo() {
		log.info("{}的方法",type);
	}

	@Override
	public String getStorageType() {
		return type;
	}

}
