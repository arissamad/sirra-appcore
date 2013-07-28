package com.sirra.appcore.rest;

public class RestException extends Exception {

	public RestException(Exception e, String message) {
		super(message, e);
	}

	public RestException(String message) {
		super(message);
	}
	
	public RestException(Exception e) {
		super(e);
	}
}
