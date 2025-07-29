package com.paiva.eurotreino.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.paiva.eurotreino.exception.AppException;
import com.paiva.eurotreino.exception.model.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(AppException.class);
	
	@ExceptionHandler(AppException.class)
	public ResponseEntity<ErrorResponse> handleAppException(AppException ex, HttpServletRequest request){
		
		logger.error("AppException: [{}] {}", ex.getCode(), ex.getMessage());
		
		ErrorResponse response = new ErrorResponse(
				ex.getStatus().value(), 
				ex.getCode(), 
				ex.getMessage(), 
				request.getRequestURI());
		
		return ResponseEntity.status(ex.getStatus()).body(response);
		
	}

}
