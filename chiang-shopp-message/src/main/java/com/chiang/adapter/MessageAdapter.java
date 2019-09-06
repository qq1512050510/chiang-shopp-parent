package com.chiang.adapter;

import com.alibaba.fastjson.JSONObject;
//统一发送消息的接口
public interface MessageAdapter {
	public void sendMsg(JSONObject body);
}
