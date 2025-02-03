package com.ifragodevs.caixank_app.exceptions;

public class ExpiredJwtException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExpiredJwtException(String message) {
		super(message);
		
	}
}
