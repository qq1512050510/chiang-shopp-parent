package com.chiang.controller;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chiang.base.ResponseBase;
import com.chiang.constant.Constants;
import com.chiang.entity.UserEntity;
import com.chiang.feign.MemberServiceFeign;
import com.chiang.utils.CookieUtil;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.oauth.Oauth;

@Controller
public class LoginController {

	@Autowired
	private MemberServiceFeign memberServiceFeign;

	private static final String LOGIN = "login";

	// private static final String INDEX = "index";
	// 重定向
	private static final String INDEX = "redirect:/";

	private static final String qqrelation = "qqrelation";

	// 跳转登录页面
	@RequestMapping(value = "/login", method = { RequestMethod.GET })
	public String loginGet() {
		return LOGIN;
	}

	// 登录请求具体提交实现
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	public String loginPost(UserEntity userEntity, HttpServletRequest request, HttpServletResponse response) {
		// 1、验证参数
		// 2、调用登录接口，获取token的信息
		ResponseBase loginBase = memberServiceFeign.login(userEntity);
		if (!loginBase.getRtnCode().equals(Constants.HTTP_RES_CODE_200)) {
			request.setAttribute("error", "账号或者密码错误！");
			return LOGIN;
		}
		LinkedHashMap loginData = (LinkedHashMap) loginBase.getData();
		String memberToken = (String) loginData.get("memberToken");
		if (StringUtils.isEmpty(memberToken)) {
			request.setAttribute("error", "会话已经失效！");
			return LOGIN;
		}
		// 3、将token信息存放到cookie里面 cookie的token尽量少一天
		setCookie(memberToken, response);
		return INDEX;
	}
	
	public void setCookie(String memberToken, HttpServletResponse response) {
		CookieUtil.addCookie(response, Constants.COOKIE_MEMBER_TOKEN, memberToken,
				Constants.TOKEN_MEMBER_TIME.intValue());
	}

	// 生成QQ授权登录链接
	@RequestMapping("/localQQLogin")
	public String localQQLogin(HttpServletRequest request) throws QQConnectException {
		// 生成授权链接
		String authorizedURL = new Oauth().getAuthorizeURL(request);
		// return "redirect:"+authorizedURL;
		return INDEX + authorizedURL;
	}

	public String qqLoginCallback(HttpServletRequest request,HttpSession httpSession) throws QQConnectException {
		// 1、获取授权码 Code
		// 2、使用授权码 Code获取accessToken
		AccessToken accessTokenOj = new Oauth().getAccessTokenByRequest(request);
		if (ObjectUtils.isEmpty(accessTokenOj)) {
			request.setAttribute("error", "QQ授权失败");
			return "error";
		}
		String accessToken = accessTokenOj.getAccessToken();
		if (StringUtils.isEmpty(accessToken)) {
			request.setAttribute("error", "accessToken为null");
			return "error";
		}
		// 3、使用accessToken获取openid
		OpenID openidOj = new OpenID(accessToken);
		String userOpenId = openidOj.getUserOpenID();
		// 4 调用会员接口 使用userOpenId 查找是否已经关联过账号
		ResponseBase openUserBase = memberServiceFeign.findByOpenIdUser(userOpenId);
		if (openUserBase.getRtnCode().equals(Constants.HTTP_RES_CODE_201)) {
			// 5 如果没有关联账号，跳转到关联账号页面
			httpSession.setAttribute("qqOpenid", userOpenId);
			return qqrelation;
		}
		//6、已经绑定账号 自动登录 将用户token信息存放到cookie中
		LinkedHashMap dataTokenMap = (LinkedHashMap) openUserBase.getData();
		//setCookie(memberToken, response);
		return null;
	}

}
