package com.chiang.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chiang.api.service.MemberService;
import com.chiang.base.BaseApiService;
import com.chiang.base.ResponseBase;
import com.chiang.dao.MemberDao;
import com.chiang.entity.UserEntity;
import com.chiang.utils.MD5Util;

import org.apache.commons.lang.StringUtils;



@RestController
@RequestMapping("/member")
public class MemberServiceImpl extends BaseApiService implements MemberService {
	
	@Autowired
	private MemberDao memberDao; 
	
	@Override
	public ResponseBase findUserById(Long userId) {
		UserEntity userEntity =  memberDao.findByID(userId);
		if(userEntity==null) {
			return setResultError("未查找到用户信息。");
		}
		return setsetResultSuccess(userEntity);
	}

	@Override
	public ResponseBase regUser(@RequestBody UserEntity user) {
		//参数验证
		String password = user.getPassword();
		if(StringUtils.isEmpty(password)) {
			return setResultError("密码不能为空");
		}
		String newPassword = MD5Util.MD5(password);
		user.setPassword(newPassword);
		Integer result = memberDao.insertUser(user);
		if(result<=0) {
			return setResultError("注册用户信息失败。");
		}
		return setsetResultSuccess(user,"用户注册成功。");
	}

}
