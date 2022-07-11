package com.taozi.common.exception;

public class TaoziException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public TaoziException(String message){
		super(message);
	}

	public TaoziException(Throwable cause)
	{
		super(cause);
	}

	public TaoziException(String message,Throwable cause)
	{
		super(message,cause);
	}
}
