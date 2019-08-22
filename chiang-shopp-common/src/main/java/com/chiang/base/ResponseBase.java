package com.chiang.base;

import lombok.Data;

@Data
public class ResponseBase {
	private Integer rtnCode;
	private String msg;
	private Object obj;
	public ResponseBase() {
		
	}
	public ResponseBase(Integer rtnCode, String msg, Object obj) {
		super();
		this.rtnCode = rtnCode;
		this.msg = msg;
		this.obj = obj;
	}
	
}
