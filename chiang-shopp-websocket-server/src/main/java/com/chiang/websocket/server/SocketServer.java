package com.chiang.websocket.server;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.PathParam;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.chiang.websocket.constants.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * @author adp
 * @Date: 2019/11/8 10:11
 * 
 * @ServerEndpoint 这个注解有什么作用？
 *
 *                 这个注解用于标识作用在类上，它的主要功能是把当前类标识成一个WebSocket的服务端
 *                 注解的值用户客户端连接访问的URL地址
 *
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/{name}")
public class SocketServer {
	// 与某个客户端的链接对话，需要通过它来给客户端方式消息
	private Session session;
	// 标识当前链接客户端的用户名
	private String name;
	// 用于存所有的链接服务的客户端，这个对象存储是安全的
	private static ConcurrentHashMap<String, SocketServer> webSocketSet = new ConcurrentHashMap<>();

	@OnOpen
	public void onOpen(Session session,@PathParam(value = "name") String name) {
		this.session = session;
		this.name = name;
		webSocketSet.put(name, this);
		log.info("[WebSocket] 连接成功，当前连接人数：={}", webSocketSet.size());
	}

	@OnClose
	public void oClose() {
		webSocketSet.remove(this.name);
		log.info("[WebSocket] 退出成功，当前连接人数为：={}", webSocketSet.size());
	}

	@OnMessage
	public void onMessage(String message) {
		log.info("[WebSocket] 收到消息： {}", message);
		// 判断是否指定发送，具体规则 TOURSE-"name"-"message"
		if (message.indexOf(Constants.TOUSER) != -1) {
			List<String> messageList = Arrays.asList(message.split("-"));
			String name = "";
			String messageSend = "";
			// 格式完成性约束
			if (!ObjectUtils.isEmpty(messageList.get(1))) {
				name = (String) messageList.get(1);
			}
			if (!ObjectUtils.isEmpty(messageList.get(2))) {
				messageSend = (String) messageList.get(2);
			}
			if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(messageSend)) {
				appointSending(name, messageSend);
			} else {
				log.error("单点发送消息格式有误( ToUser-\"name\"-\"message\"):{}", message);
			}
		} else {
			groupSending(message);
		}
	}

	@OnError
	public void OnError(Session session, Throwable error) {
		log.error("发生错误");
		error.printStackTrace();
	}

	/**
	 * 指定用户发送消息
	 * 
	 * @param name
	 * @param message
	 */
	private void appointSending(String name, String message) {
		try {
			log.info("appointSending");
			webSocketSet.get(name).session.getBasicRemote().sendText(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void groupSending(String message) {
		try {
			log.info("groupSending");
			for (SocketServer webSocket : webSocketSet.values()) {
				webSocket.session.getBasicRemote().sendText(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
