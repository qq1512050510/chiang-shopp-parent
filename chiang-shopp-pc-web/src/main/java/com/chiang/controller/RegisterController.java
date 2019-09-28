package com.chiang.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chiang.base.ResponseBase;
import com.chiang.constant.Constants;
import com.chiang.entity.UserEntity;
import com.chiang.feign.MemberServiceFeign;

@Controller
public class RegisterController {

	@Autowired
	private MemberServiceFeign memberServiceFeign;

	private static final String REGISTER = "register";
	private static final String LOGIN = "login";

	@RequestMapping(value = "/register", method = { RequestMethod.GET, RequestMethod.POST })
	public String register() {
		return REGISTER;
	}

	// 注册业务具体实现
	@RequestMapping(value = "/register", method = { RequestMethod.GET, RequestMethod.POST })
	public String registerPost(UserEntity userEntity, HttpServletRequest request) {
		// 1、验证参数
		// 2、调用会员注册接口
		ResponseBase regUser = memberServiceFeign.regUser(userEntity);
		// 3、如果失败，跳转失败页面
		if (!regUser.getRtnCode().equals(Constants.HTTP_RES_CODE_200)) {
			request.setAttribute("error", "注册失败");
		}
		// 4、如果成功，跳转成功页面
		else {
			request.setAttribute("data", regUser);
		}
		return LOGIN;
	}

}
