package com.chiang.api.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;

import com.chiang.api.service.TestApiService;
import com.chiang.base.BaseApiService;
import com.chiang.base.ResponseBase;
@RestController
public class TestApiServiceImpl extends BaseApiService implements TestApiService{
	
	
	@Override
	public Map<String, Object> test(Integer id, String name) {
		Map<String,Object> result = new HashMap<>();
		result.put("errorCode", "200");
		result.put("msg","success");
		result.put("data", new String[]{"123","1132423",id+"",name});
		return result;
	}

	@Override
	public ResponseBase testResponseBase() {
		return setsetResultSuccess();
	}

	@Override
	public ResponseBase testRedis(String key,String value) {
		baseRedisService.setString(key, value, null);
		return setsetResultSuccess();
	}

}
