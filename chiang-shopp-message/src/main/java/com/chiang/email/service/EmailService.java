package com.chiang.email.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.chiang.adapter.MessageAdapter;

import lombok.extern.slf4j.Slf4j;
//处理第三方发送邮件
@Slf4j
@Service
public class EmailService implements MessageAdapter{
	@Value("${msg.subject}")
	private String subject;
	@Value("${msg.text}")
	private String text;
	@Value("${spring.mail.username}")
	private String emailForm;
	@Autowired
	private JavaMailSender mailSender;
	@Override
	public void sendMsg(JSONObject body) {
		//处理发送邮件
		String email = body.getString("email");
		if(StringUtils.isEmpty(email)) {
			return ;
		}
		//邮件信息
		log.info("消息服务平台发送消息：{}",email);
		log.info("发送账号：{}",emailForm);
		log.info("接收账号：{}",email);
		log.info("账号：{}",subject);
		log.info("内容：{}",text);
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		//发送
		simpleMailMessage.setFrom(emailForm);
		simpleMailMessage.setTo(email);
		//标题
		simpleMailMessage.setSubject(subject);
		//内容
		simpleMailMessage.setText(text.replace("{}", email));
		mailSender.send(simpleMailMessage);
	}

}
