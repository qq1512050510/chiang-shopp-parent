package com.chiang.exception;

public class UnknownStorageTypeException extends RuntimeException {

	private static final long serialVersionUID = -9051058795804353682L;

	public UnknownStorageTypeException(String message) {
		super(message);
	}
}
