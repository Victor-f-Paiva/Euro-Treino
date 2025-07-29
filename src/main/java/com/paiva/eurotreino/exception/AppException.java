package com.paiva.eurotreino.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class AppException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private String code;
	private HttpStatus status;
	
	public AppException(String code, HttpStatus status, String message) {
		super(message);
		this.code = code;
		this.status = status;
	}
	
	

}
