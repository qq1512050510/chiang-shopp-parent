package com.chiang.controller;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chiang.base.ResponseBase;
import com.chiang.constant.Constants;
import com.chiang.entity.UserEntity;
import com.chiang.feign.MemberServiceFeign;
import com.chiang.utils.CookieUtil;


@Controller
public class LoginController {
	
	
	@Autowired
	private MemberServiceFeign memberServiceFeign;
	
	private static final String LOGIN = "login";
	
	//private static final String INDEX = "index";
	//重定向
	private static final String INDEX = "redirect:/";
	//跳转登录页面
	@RequestMapping(value="/login",method={RequestMethod.GET})
	public String loginGet() {
		return LOGIN;
	}
	//登录请求具体提交实现
	@RequestMapping(value="/login",method={RequestMethod.POST})
	public String loginPost(UserEntity userEntity,HttpServletRequest request,HttpServletResponse response) {
		//1、验证参数
		//2、调用登录接口，获取token的信息
		ResponseBase loginBase = memberServiceFeign.login(userEntity);
		if(!loginBase.getRtnCode().equals(Constants.HTTP_RES_CODE_200)) {
			request.setAttribute("error", "账号或者密码错误！");
			return LOGIN;
		}
		LinkedHashMap loginData = (LinkedHashMap) loginBase.getData();
		String memberToken = (String)loginData.get("memberToken");
		if(StringUtils.isEmpty(memberToken)) {
			request.setAttribute("error", "会话已经失效！");
			return LOGIN;
		}
		//3、将token信息存放到cookie里面 cookie的token尽量少一天
		CookieUtil.addCookie(response,Constants.COOKIE_MEMBER_TOKEN, memberToken, Constants.TOKEN_MEMBER_TIME.intValue());
		return INDEX;
	}
	
	
	
	
	
	
	
	
}
