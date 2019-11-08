package com.chiang.websocket.service;
/**
 * 
 * @author 
 *
 */
public interface WebSocketService {
	//群发
	void groupSending(String message);
	//指定发送
	void appointSending(String name,String message);
}
