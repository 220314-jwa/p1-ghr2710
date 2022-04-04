package dev.roberts.exceptions;

public class UserDoesNotExistException extends Exception{
	
	public UserDoesNotExistException() {
		super("The user requested does not exist");
	}

}
