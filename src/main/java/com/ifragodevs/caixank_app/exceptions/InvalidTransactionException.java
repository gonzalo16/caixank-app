package com.ifragodevs.caixank_app.exceptions;

public class InvalidTransactionException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidTransactionException(String message) {
        super(message);
    }
}
