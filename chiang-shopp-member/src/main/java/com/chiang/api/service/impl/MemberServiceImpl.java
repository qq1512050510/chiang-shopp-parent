package com.chiang.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.chiang.api.service.MemberService;
import com.chiang.base.BaseApiService;
import com.chiang.base.BaseRedisService;
import com.chiang.base.ResponseBase;
import com.chiang.constant.Constants;
import com.chiang.dao.MemberDao;
import com.chiang.entity.UserEntity;
import com.chiang.mq.RegisterMailboxProducer;
import com.chiang.utils.MD5Util;
import com.chiang.utils.TokenUtil;

import lombok.extern.slf4j.Slf4j;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang.StringUtils;

@Slf4j
@RestController
// @RequestMapping("/member")
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
		return setResultSuccess(userEntity);
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
		return setResultSuccess(user, "用户注册成功。");
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
	public ResponseBase login(@RequestBody UserEntity user) {
		// 1、验证参数
		String username = user.getUsername();
		if (StringUtils.isEmpty(username)) {
			return setResultError("用户名不能为空！");
		}
		String password = user.getPassword();
		if (StringUtils.isEmpty(password)) {
			return setResultError("密码不能为空！");

		}
		String newPassword = MD5Util.MD5(password);
		// 2、数据库查找账号密码是否正确
		UserEntity userEntity = memberDao.login(username, newPassword);
		return setLogin(userEntity);
	}

	// 通用代码封装
	private ResponseBase setLogin(UserEntity userEntity) {
		if (userEntity == null) {
			return setResultError("账号或者密码不正确！");
		}
		// 3、如果账号正确，对应生成token
		String memberToken = TokenUtil.getMemberToken();
		// 4、存放redis中，key为token value为userid
		Integer userId = userEntity.getId();
		log.info("##用户信息token存放在redis中...key为：{}，value{}", memberToken, userId);
		baseRedisService.setString(memberToken, userId + "", Constants.TOKEN_MEMBER_TIME);
		// 5、直接返回token
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("memberToken", memberToken);
		return setResultSuccess(jsonObject);
	}

	@Override
	// public ResponseBase findUserByToken(@RequestParam(value = "token",required =
	// false) String token) {
	public ResponseBase findUserByToken(@RequestParam("token") String token) {
		// public ResponseBase findUserByToken(@RequestParam(value="token")String token)
		// {
		log.info("token is :{}", token);
		// 1、验证参数
		if (StringUtils.isEmpty(token)) {
			return setResultError("token不能为空");
		}
		// 2、从Redis中，使用token，查了对应的userId
		String userId = (String) baseRedisService.getString(token);
		// 3、使用UserId数据库查询用户返回客户端
		log.info(userId);
		if (StringUtils.isEmpty(userId)) {
			return setResultError("token无效或者已经过期！");
		}
		UserEntity userEntity = memberDao.findByID(Long.parseLong(userId));
		if (ObjectUtils.isEmpty(userEntity)) {
			return setResultError("未查找到该用户信息");
		}
		userEntity.setPassword(null);
		return setResultSuccess(userEntity);

	}

	@Override
	public ResponseBase findByOpenIdUser(String openid) {
		// 1.验证参数
		if (StringUtils.isEmpty(openid)) {
			return setResultError("系统错误！");
		}
		// 2.使用openid 查询数据库user表对应的数据信息
		UserEntity userEntity = memberDao.findByOpenIdUser(openid);
		if (userEntity == null) {
			return setResultError(Constants.HTTP_RES_CODE_201, "token无效或者已经过期！");
		}
		// 3.自动登录
		return setLogin(userEntity);
	}

	@Override
	public ResponseBase qqLogin(UserEntity user) {
		// 1.验证参数
		String openid = user.getOpenid();
		Integer userid = user.getId();
		if (StringUtils.isEmpty(openid)) {
			return setResultError("openid不能为空");
		}
		// 2.先进行账号登录
		ResponseBase setLogin = setLogin(user);
		if (setLogin.getRtnCode().equals(Constants.HTTP_RES_CODE_200)) {
			return setLogin;
		}
		// 3.登录成功,数据库修改对应的openid
		Integer updateByOpenIdUser = memberDao.updateByOpenIdUser(openid, userid);
		if(updateByOpenIdUser<=0) {
			return setResultError("QQ账号关联失败！");
		}
		return setLogin;
	}

}
