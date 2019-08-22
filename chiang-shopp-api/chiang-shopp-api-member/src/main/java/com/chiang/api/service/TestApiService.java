package com.chiang.api.service;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/member")
public interface TestApiService {
	@RequestMapping("/test")
	public Map<String,Object>test(Integer id,String name);
}
