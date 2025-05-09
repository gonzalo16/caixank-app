package com.ifragodevs.caixank_app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;

@RestControllerAdvice
public class HandlerExceptionController {

	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<Object> jwtExpired(Exception e){
		return new ResponseEntity<Object>("Token provided is expired",HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(MalformedJwtException.class)
	public ResponseEntity<Map<String, String>> handledMalFormedJwtException(MalformedJwtException e){
		Map<String, String> response = new HashMap<>();
        response.put("error", "Token JWT mal formado");
        response.put("message", e.getMessage());
        
        return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(SignatureException.class)
	public ResponseEntity<Map<String, String>> handledSignatureException(SignatureException e){
		Map<String, String> response = new HashMap<>();
        response.put("error", "La firma JWT no coincide con la firma calculada localmente. La validez de JWT no se puede afirmar y no se debe confiar en ella.");
        response.put("message", e.getMessage());
        
        return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
	}
}
