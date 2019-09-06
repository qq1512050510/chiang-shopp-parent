package com.chiang.mq;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chiang.adapter.MessageAdapter;
import com.chiang.constant.Constants;
import com.chiang.email.service.EmailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ConsumerDistribute {
	@Autowired
	private EmailService emailService;
	private MessageAdapter messageAdapter;
	@JmsListener(destination = "messages_queue")
	public void distribute(String json) {
		log.info("####ConsumerDistribute###distribute() 消息服务平台接受 json参数:" + json);
		if (StringUtils.isEmpty(json)) {
			return;
		}
		//JSONObject jsonObecjt = new JSONObject().parseObject(json);
		JSONObject jsonObecjt = JSON.parseObject(json);
		JSONObject header = jsonObecjt.getJSONObject("header");
		String interfaceType = header.getString("interfaceType");

		if (StringUtils.isEmpty(interfaceType)) {
			return;
		}
		if (interfaceType.equals(Constants.SMS_MAIL)) {
			messageAdapter = emailService;
		}
		if (messageAdapter == null) {
			return;
		}
		JSONObject body = jsonObecjt.getJSONObject("content");
		 messageAdapter.sendMsg(body);
	}
	
	@JmsListener(destination = "message_queue_test")
	public void distributeTest(String json) {
		log.info("##消息服务平台接受消息内容：{}##",json);
		if(StringUtils.isEmpty(json)) {
			return ;
		}
		//JSONObject rootJSON = new JSONObject().parseObject(json);
		JSONObject rootJSON = JSON.parseObject(json);
		JSONObject header = rootJSON.getJSONObject("header");
		String interfaceType = header.getString("interfaceType");
		if(StringUtils.isEmpty(interfaceType)) {
			return;
		}
		//判断接口类型是否为发邮件
		if(interfaceType.equals(Constants.MSG_EMAIL)){
			messageAdapter = emailService;	
		}
		if(interfaceType.equals(null)) {
			return ;
		}
		JSONObject contentJson = rootJSON.getJSONObject("content");
		messageAdapter.sendMsg(contentJson);
	}
}

