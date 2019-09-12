package com.chiang.api.service;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chiang.base.ResponseBase;
//import com.chiang.entity.UserEntity;
import com.chiang.entity.UserEntity;

public interface MemberService {
	//使用UserId查找用户信息
	@RequestMapping("/findUserById")
	ResponseBase findUserById(Long userId);
	
	//注册用户
	@RequestMapping("/regUser")
	ResponseBase regUser(@RequestBody UserEntity user);
	
	
	//登录信息
	ResponseBase login(@RequestBody UserEntity user);
}

