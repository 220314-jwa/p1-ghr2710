package dev.roberts.exceptions;

public class IncorrectCredsException extends Exception{
	
	public IncorrectCredsException() {
		super("The username or password is incorrect");
	}

}
