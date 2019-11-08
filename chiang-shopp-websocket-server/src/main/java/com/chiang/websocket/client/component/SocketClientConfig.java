package com.chiang.websocket.client.component;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SocketClientConfig {

	private static final String uri = "ws://127.0.0.1:8011/websocket/testWeb";

	@Bean
	public WebSocketClient webSocketClient() {
		try {
			WebSocketClient webSocketClient = new WebSocketClient(new URI(uri), new Draft_6455()) {

				@Override
				public void onOpen(ServerHandshake arg0) {
					log.info("[websocket]:\"{}\" 连接成功 ", uri);
				}

				@Override
				public void onMessage(String message) {
					log.info("[websocket]:\"{}\" 收到消息：{} ", uri, message);
				}

				@Override
				public void onError(Exception e) {
					log.error("[websocket]:\"{}\" 连接出错={}", uri, e.getMessage());
				}

				@Override
				public void onClose(int arg0, String arg1, boolean arg2) {
					log.info("[websocket]:\"{}\" 退出连接", uri);
				}
			};
			webSocketClient.connect();
			return webSocketClient;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
