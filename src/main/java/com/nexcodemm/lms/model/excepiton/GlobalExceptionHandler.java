package com.nexcodemm.lms.model.excepiton;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{
	
	
	@ExceptionHandler(value = {RuntimeException.class})
	public ResponseEntity<?> handleRuntimeException(RuntimeException ex){
		throw ex;	
	}
	
//	@ExceptionHandler(value = {IOException.class})
//	public ResponseEntity<String> handleIOException(IOException exception){
//		throw new BadRequestException(exception.getMessage());		
//	}
//	
//	@ExceptionHandler(value = {BadCredentialsException.class})
//	public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException exception){
//		throw new BadCredentialsException("Username or Password is incorrect!");		
//	}
	
}
