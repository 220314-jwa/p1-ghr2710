package dev.roberts;

import dev.roberts.Person;

public class User {
	 
	private String uName, password, role;
	private Person person;
	
	public User(){
		this.person = new Person();
		this.uName = "Unassigned";
		this.password = "Password";
		this.role = "NONE";
	}
	
	User(Person p, String uName, String pass, String role){
		this.person = p;
		this.uName = uName;
		this.password = pass;
		this.role = role;
	}
	
	public String getUser() {
		return uName;
	}
	
	public String getPass() {
		return password;
	}
	
	public String getRole() {
		return role;
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
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public void setPerson(Person p) {
		person = p;
	}
}
