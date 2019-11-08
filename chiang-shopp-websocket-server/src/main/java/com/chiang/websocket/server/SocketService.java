package com.chiang.websocket.server;
/**
 * 
 * @author 
 *
 */
public interface SocketService {
	//群发
	void groupSending(String message);
	//指定发送
	void appointSending(String name,String message);
	//是否有连接
	boolean hasOrNotConnection();
}
