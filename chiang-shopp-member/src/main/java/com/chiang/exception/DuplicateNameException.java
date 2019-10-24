package com.chiang.exception;

public class DuplicateNameException extends RuntimeException {

	private static final long serialVersionUID = -8878108348709411364L;

	public DuplicateNameException(String message) {
		super(message);
	}
	
}
