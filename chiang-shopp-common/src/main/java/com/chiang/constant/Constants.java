package com.chiang.constant;

import org.springframework.http.HttpStatus;

public interface Constants {
	// 响应code
	String HTTP_RES_CODE_NAME = "code";
	// 响应msg
	String HTTP_RES_CODE_MSG = "msg";
	// 响应data
	String HTTP_RES_CODE_DATA = "data";
	
	final int SUCCESS = 0;
	
	final int FAIL = -1;
	
	// 响应请求成功
	String HTTP_RES_CODE_200_VALUE = "success";
	// 系统错误
	String HTTP_RES_CODE_500_VALUE = "fial";
	// 响应请求成功code
	//Integer HTTP_RES_CODE_200 = 200;
	Integer HTTP_RES_CODE_200 = HttpStatus.OK.value();
	
	// 系统错误
	//Integer HTTP_RES_CODE_500 = 500;
	Integer HTTP_RES_CODE_500 = HttpStatus.INTERNAL_SERVER_ERROR.value();
	
	//未关联QQ账号
	Integer HTTP_RES_CODE_201 = 201;
	
	String SMS_MAIL = "email";

	String MSG_SMS = "email";

	String MSG_EMAIL = "email";
	// 会员token
	String TOKEN_MEMBER = "TOKEN_MEMBER";
	
	// 会员token过期时间 90 天
	Long TOKEN_MEMBER_TIME = (long) (90 * 24 * 60 * 60);
	
	//cookie 会员token 名称
	String COOKIE_MEMBER_TOKEN = "cookie_member_token";
	
	String BASE_SERVICE1 = "base_service1";
	
	String BASE_SERVICE2 = "base_service2";

}
