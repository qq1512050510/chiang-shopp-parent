package com.chiang.exception;

/**
 * 未授权 异常
 * @author Administrator
 *
 */
public class UnauthenticatedException extends RuntimeException {

	private static final long serialVersionUID = -2514163858893014645L;

	public UnauthenticatedException(String message) {
		super(message);
	}

}
