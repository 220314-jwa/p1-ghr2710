package dev.roberts;
import dev.roberts.Person;
import dev.roberts.User;
import dev.roberts.DBHandler;
import dev.roberts.exceptions.IncorrectCredsException;
import dev.roberts.exceptions.UserAlreadyExistsException;
import dev.roberts.exceptions.UserDoesNotExistException;

public class UserService {
	
	DBHandler handler;
	
	public UserService(){
		handler = new DBHandler();
	}
	
	public User login(String uName, String pWord) throws IncorrectCredsException {
		Person p = new Person();
		User u = new User();
		
		Boolean exists = false;
		try {
			exists = handler.queryUserExists(uName);
		}
		catch (Exception e) {
			System.out.println("User exists query went wrong");
		}
		
		if (exists) {
			try {
				u = handler.queryGetUser(uName);
			}
			catch (Exception e) {
				System.out.println("Request user query went wrong");
			}
			if (u != null) {
				if (pWord.equals(u.getPass())) {
					System.out.println("Login Successful");
				}
				else {
					throw new IncorrectCredsException();
				}
			}
			else {
				return null;
			}
		}
		else {
			throw new IncorrectCredsException();
		}
		
		return u;
	}
	
	public Boolean createAcct(User u) throws UserAlreadyExistsException{
		Boolean exists = false;
		try {
			exists = handler.queryUserExists(u.getUser());
		}
		catch (Exception e) {
			//User exists query went wrong
			return false;
		}
		
		Boolean userCreated = false;
		Boolean personCreated = false;
		
		if (!exists) {
			try {
				userCreated = handler.queryCreateUser(u);
				personCreated = handler.queryCreatePerson(u);
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
		else {
			//That username already exists
			throw new UserAlreadyExistsException();
		}
		if (userCreated && personCreated) {
			//User created successfully
			return true;
		}
		else {
			//User creation failed due to SQL error
			return false;
		}
	}
	
	public Boolean deleteAcct(User u) throws UserDoesNotExistException{
		Boolean exists = false;
		try {
			exists = handler.queryUserExists(u.getUser());
		}
		catch (Exception e) {
			//User exists query went wrong
			return false;
		}
		
		Boolean userDeleted = false;
		Boolean personDeleted = false;
		
		if (exists) {
			try {
				personDeleted = handler.queryDeletePerson(u);
				userDeleted = handler.queryDeleteUser(u);
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
		else {
			//That user does not exist
			throw new UserDoesNotExistException();
		}
		if (userDeleted && personDeleted) {
			//User deleted successfully
			return true;
		}
		else {
			//User delete failed due to SQL error
			return false;
		}
	}

}
