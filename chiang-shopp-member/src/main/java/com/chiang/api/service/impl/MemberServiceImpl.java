package com.chiang.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chiang.api.service.MemberService;
import com.chiang.base.BaseApiService;
import com.chiang.base.ResponseBase;
import com.chiang.dao.MemberDao;
import com.chiang.entity.UserEntity;



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

}
