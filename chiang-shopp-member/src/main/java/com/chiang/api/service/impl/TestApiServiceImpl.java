package com.chiang.api.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
	@RequestMapping(value = "testResponseBase", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
	public ResponseBase testResponseBase() {
		return setResultSuccess();
	}

	@Override
	public ResponseBase testRedis(String key,String value) {
		baseRedisService.setString(key, value, null);
		return setResultSuccess();
	}

	@Override
	@RequestMapping(value = "testSh/{fileName}", method = RequestMethod.GET, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
	public String testSh(@PathVariable("fileName")String fileName) {
		return "echo \"123\"\necho \"tt\"";
	}

}
