package com.chiang.utils;

import java.util.UUID;

import com.chiang.constant.Constants;

public class TokenUtil {
	public static String getMemberToken() {
		return Constants.TOKEN_MEMBER+"-"+UUID.randomUUID();
	}
}
