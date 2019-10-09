package com.chiang.controller;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chiang.base.ResponseBase;
import com.chiang.constant.Constants;
import com.chiang.feign.MemberServiceFeign;
import com.chiang.utils.CookieUtil;

@Controller
public class IndexController {
	private static final String INDEX = "index";
	
	@Autowired
	private MemberServiceFeign memberServiceFeign;
	
	//主页
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String index(HttpServletRequest request,HttpServletResponse response) {
		//1、从cookie中过去token信息
		String token = CookieUtil.getUid(request, Constants.COOKIE_MEMBER_TOKEN);
		//2、如果cookie 存在token
		if(!StringUtils.isEmpty(token)) {
			ResponseBase responseBase = memberServiceFeign.findUserByToken(token);
			if(responseBase.getRtnCode().equals(Constants.HTTP_RES_CODE_200)) {
				LinkedHashMap userData = (LinkedHashMap) responseBase.getObj();
				String username = (String) userData.get("userName");
				request.setAttribute("username", username);
			}
		}
		return INDEX;
	}
}
