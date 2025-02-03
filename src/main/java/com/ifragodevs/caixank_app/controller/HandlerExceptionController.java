package com.ifragodevs.caixank_app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice
public class HandlerExceptionController {

	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<Object> jwtExpired(Exception e){
		return new ResponseEntity<Object>("Token provided is expired",HttpStatus.UNAUTHORIZED);
	}
}
