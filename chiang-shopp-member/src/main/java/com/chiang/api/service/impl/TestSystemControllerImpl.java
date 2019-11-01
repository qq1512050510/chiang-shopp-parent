package com.chiang.api.service.impl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chiang.information.Server;

@RestController
@RequestMapping("/system")
public class TestSystemControllerImpl {
	
	/**
	 * 系统监控接口
	 * 整个系统状态
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/index")
	public Server index() throws Exception {
		Server server = new Server();
		server.copyTo();
		return server;
	}
}
