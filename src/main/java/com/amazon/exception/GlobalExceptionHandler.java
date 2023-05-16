package com.amazon.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazon.model.response.Error;
import com.fasterxml.jackson.core.JsonProcessingException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(JsonProcessingException.class)
	public ResponseEntity<Error> jsonProcessingException(JsonProcessingException exception) {
		Error error = Error.builder().errorId(10).errorCode(200).errorMessage(exception.getMessage()).build();
		return new ResponseEntity<Error>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}