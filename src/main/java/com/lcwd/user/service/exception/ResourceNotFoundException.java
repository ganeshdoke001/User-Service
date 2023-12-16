package com.lcwd.user.service.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException() {
		super("Resource not found on servers ...!!");
	}
	
	public ResourceNotFoundException(String message) {
		super(message);
	}

}
