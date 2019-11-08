package com.chiang.websocket.server.impl;

import org.java_websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.chiang.websocket.constants.Constants;
import com.chiang.websocket.server.SocketServer;
import com.chiang.websocket.server.SocketService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SocketClient implements SocketService {
	

	@Autowired
	private WebSocketClient webSocketClient;

	@Override
	public void groupSending(String message) {
		if (hasOrNotConnection()) {
			webSocketClient.send(message);
		} else {
			log.info("[WebSocket]未连接");
		}
	}

	@Override
	public void appointSending(String name, String message) {
		if (hasOrNotConnection()) {
			webSocketClient.send(Constants.TOUSER + "-" + name + "-" + message);
		} else {
			log.info("[WebSocket]未连接");
		}
	}

	@Override
	public boolean hasOrNotConnection() {
		boolean returnB = true;
		if(webSocketClient.getConnection()==null) {
			returnB = false;
		}
		return returnB;
	}

}
