package com.paiva.eurotreino.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends AppException{

	private static final long serialVersionUID = 1L;

	public NotFoundException(String message) {
		super("NOT_FOUND", HttpStatus.NOT_FOUND, message);
	}

}
