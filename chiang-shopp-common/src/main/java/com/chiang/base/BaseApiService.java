package com.chiang.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chiang.constant.Constants;

@Component
public class BaseApiService {
	
	@Autowired
	protected BaseRedisService baseRedisService;
	
	//返回成功，可以传data值 ,msg
	public ResponseBase setResultSuccess(Object data,String msg) {
		return setResult(Constants.HTTP_RES_CODE_200, msg, data);
	}
	
	//返回成功，可以传msg
	public ResponseBase setResultSuccess(String msg) {
		return setResult(Constants.HTTP_RES_CODE_200, msg, null);
	}
	
	//返回成功，没有data值
	public ResponseBase setResultSuccess() {
		return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, null);
	}
	
	//返回错误，信息msg
	public ResponseBase setResultError(Integer code,String msg) {
		return setResult(code, msg, null);
	}
	
	//返回错误，信息msg
	public ResponseBase setResultError(String msg) {
		return setResult(Constants.HTTP_RES_CODE_500, msg, null);
	}
	
	//返回成功，可以传data值
	public ResponseBase setResultSuccess(Object data) {
		return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, data);
	}

	// 通用封装
	public ResponseBase setResult(Integer code, String msg, Object data) {
		return new ResponseBase(code, msg, data);
	}
}
