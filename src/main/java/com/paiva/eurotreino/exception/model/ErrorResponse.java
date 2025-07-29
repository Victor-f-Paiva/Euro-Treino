package com.paiva.eurotreino.exception.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorResponse {
	
	private LocalDateTime stamp;
	private int status;
	private String code;
	private String message;
	private String path;
	
	public ErrorResponse(int status, String code, String message, String path) {
		this.stamp = LocalDateTime.now();
		this.status = status;
		this.code = code;
		this.message = message;
		this.path = path;
	}
	
}
