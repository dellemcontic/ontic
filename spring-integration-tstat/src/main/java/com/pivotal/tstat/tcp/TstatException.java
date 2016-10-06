package com.pivotal.tstat.tcp;

public class TstatException extends Exception{

	private static final long serialVersionUID = 1L;

	public TstatException(String description)
	{
		super(description);
	}
}
