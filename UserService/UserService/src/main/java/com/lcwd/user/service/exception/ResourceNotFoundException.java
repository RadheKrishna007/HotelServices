package com.lcwd.user.service.exception;

public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException() {
		super("Resouces not found on server");
	}
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
	
}
