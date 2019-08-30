package com.chiang.api.service;

import org.springframework.web.bind.annotation.RequestMapping;

import com.chiang.base.ResponseBase;
//import com.chiang.entity.UserEntity;

public interface MemberService {
	//使用UserId查找用户信息
	@RequestMapping("/findUserById")
	ResponseBase findUserById(Long userId);
}
