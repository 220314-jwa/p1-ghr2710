package test.dev.roberts;
import test.dev.roberts.Person;

public class User {
	 
	private String uName, password;
	private Person person;
	
	User(Person p, String uName, String pass){
		this.person = p;
		this.uName = uName;
		this.password = pass;
	}
	
	public String getUser() {
		return uName;
	}
	
	public String getPass() {
		return password;
	}
	
	public Person getPerson() {
		return person;
	}
	
	public void setUser(String userName) {
		uName = userName;
	}
	
	public void setPass(String pass) {
		password = pass;
	}
	
	public void setPerson(Person p) {
		person = p;
	}
}
