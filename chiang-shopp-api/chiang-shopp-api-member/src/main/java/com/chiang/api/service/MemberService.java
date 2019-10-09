package com.chiang.api.service;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chiang.base.ResponseBase;
//import com.chiang.entity.UserEntity;
import com.chiang.entity.UserEntity;

@RequestMapping("/member")
public interface MemberService {
	//使用UserId查找用户信息
	@RequestMapping("/findUserById")
	ResponseBase findUserById(Long userId);
	
	//注册用户
	@RequestMapping("/regUser")
	ResponseBase regUser(@RequestBody UserEntity user);
	
	
	//用户登录
	@RequestMapping("/login")
	ResponseBase login(@RequestBody UserEntity user);
	
	//使用token进行登录
	//@PostMapping("/findUserByToken")
	@RequestMapping("/findUserByToken")
	ResponseBase findUserByToken(@RequestParam("token")String token);
}

