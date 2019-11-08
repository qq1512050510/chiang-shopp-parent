package com.chiang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chiang.websocket.server.SocketServer;
import com.chiang.websocket.server.SocketService;

@RestController
@RequestMapping("/websocket")
public class WebSocketController {
	
	@Autowired
	private SocketService webSocketClient;
	
	@Autowired
	private SocketServer socketServer;

	@GetMapping("/sendMessage")
	public String sendMessage(@RequestParam("message") String message) {
		//webSocketClient.groupSending(message);
		socketServer.onMessage(message);
		return message;
	}
}
