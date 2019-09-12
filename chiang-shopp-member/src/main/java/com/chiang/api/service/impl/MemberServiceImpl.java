package com.chiang.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.chiang.api.service.MemberService;
import com.chiang.base.BaseApiService;
import com.chiang.base.ResponseBase;
import com.chiang.constant.Constants;
import com.chiang.dao.MemberDao;
import com.chiang.entity.UserEntity;
import com.chiang.mq.RegisterMailboxProducer;
import com.chiang.utils.MD5Util;

import lombok.extern.slf4j.Slf4j;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang.StringUtils;

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberServiceImpl extends BaseApiService implements MemberService {

	@Autowired
	private MemberDao memberDao;

	@Autowired
	private RegisterMailboxProducer registerMailboxProducer;

	// @Value("${MESSAGESQUEUE}")
	@Value("${messages.queue}")
	private String MESSAGESQUEUE;

	@Override
	public ResponseBase findUserById(Long userId) {
		UserEntity userEntity = memberDao.findByID(userId);
		if (userEntity == null) {
			return setResultError("未查找到用户信息。");
		}
		return setsetResultSuccess(userEntity);
	}

	@Override
	public ResponseBase regUser(@RequestBody UserEntity user) {
		// 参数验证
		String password = user.getPassword();
		if (StringUtils.isEmpty(password)) {
			return setResultError("密码不能为空");
		}
		String newPassword = MD5Util.MD5(password);
		user.setPassword(newPassword);
		Integer result = memberDao.insertUser(user);
		if (result <= 0) {
			return setResultError("注册用户信息失败。");
		}
		// 采用异步方式发送消息
		String email = user.getEmail();
		String json = emailJson(email);
		log.info("##会员服务推送消息到消息服务平台##json:{}", json);
		sendMsg(json);
		return setsetResultSuccess(user, "用户注册成功。");
	}

	// 参数封装
	private String emailJson(String email) {
		JSONObject rootJson = new JSONObject();
		JSONObject header = new JSONObject();
		header.put("interfaceType", Constants.MSG_EMAIL);
		JSONObject content = new JSONObject();
		content.put("email", email);
		rootJson.put("header", header);
		rootJson.put("content", content);
		return rootJson.toJSONString();
	}

	private void sendMsg(String json) {
		ActiveMQQueue activeMQQueue = new ActiveMQQueue(MESSAGESQUEUE);
		registerMailboxProducer.sendMsg(activeMQQueue, json);
	}

	@Override
	public ResponseBase login(UserEntity user) {
		//1、验证参数
		String username = user.getUsername();
		if(StringUtils.isEmpty(username)) {
			return setResultError("用户名不能为空！");
		}
		String password = user.getPassword();
		if(StringUtils.isEmpty(password)) {
			return setResultError("密码不能为空！");
			
		}
		String newPassword = MD5Util.MD5(password);
		//2、数据库查找账号密码是否正确
		UserEntity userEntity = memberDao.login(username,newPassword);
		if(userEntity==null) {
			return setResultError("账号或者密码不正确！");
		}
		//3、如果账号正确，对应生成token
		
		//4、存放redis中，key为token value为userid
		//5、直接返回token
		return null;
	}

}
