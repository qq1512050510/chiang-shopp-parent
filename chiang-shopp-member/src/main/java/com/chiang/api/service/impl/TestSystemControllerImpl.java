package com.chiang.api.service.impl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chiang.information.Server;

@RestController
@RequestMapping("/system")
public class TestSystemControllerImpl {
	@GetMapping("/index")
	public String index() throws Exception {
		Server server = new Server();
		server.copyTo();
		return server.toString();
	}
}
