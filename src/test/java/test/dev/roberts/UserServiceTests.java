package test.dev.roberts;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Date;
import dev.roberts.Person;
import dev.roberts.User;
import dev.roberts.Story;
import dev.roberts.DBHandler;
import dev.roberts.UserService;
import dev.roberts.exceptions.IncorrectCredsException;
import dev.roberts.exceptions.UserAlreadyExistsException;
import dev.roberts.exceptions.UserDoesNotExistException;

public class UserServiceTests {
	
	private UserService service = new UserService();

	@Test
	public void loginSuccess() throws IncorrectCredsException {
		String uName = "TestAuthor";
		String pass = "AuthPass";
		
		User u = service.login(uName, pass);
		
		assertEquals(uName, u.getUser());
	}

	@Test
	public void loginWrongUsername() {
		String uName = "Test";
		String pass = "AuthPass";
		
		assertThrows(IncorrectCredsException.class, () -> {
			User u = service.login(uName, pass);
		});
		
	}
	
	@Test
	public void loginWrongPassword() {
		String uName = "TestAuthor";
		String pass = "Password";
		
		assertThrows(IncorrectCredsException.class, () -> {
			User u = service.login(uName, pass);
		});
		
	}
	
	@Test
	public void deleteAcctSuccess() throws UserDoesNotExistException{
		User u = new User();
		
		Boolean b = service.deleteAcct(u);
		
		assertEquals(b, true);
		
	}
	
	@Test
	public void deleteAcctFail() {
		User u = new User();
		
		assertThrows(UserDoesNotExistException.class, () -> {
			service.deleteAcct(u);
		});
		
	}
	
	@Test
	public void createUser() throws UserAlreadyExistsException{
		User u = new User();
		
		Boolean b = service.createAcct(u);
		
		assertEquals(b, true);
		
	}
	
	@Test
	public void createUsernameTaken() {
		User u = new User();
		
		assertThrows(UserAlreadyExistsException.class, () -> {
			service.createAcct(u);
		});
		
	}
}
