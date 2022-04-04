package dev.roberts;
import dev.roberts.Person;
import dev.roberts.User;
import dev.roberts.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class DBHandler {
	
	Connection connection;
	
	public DBHandler() {
		connection = ConnectionFactory.getConnection();
	}
	
	public Boolean queryUserExists(String uName) throws SQLException {
		
		String query = "SELECT * FROM usertable WHERE uname = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, uName);
		ResultSet res = preparedStatement.executeQuery();
		if (res.next()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public User queryGetUser(String uName) throws SQLException {
		
		Person p = new Person();
		User u = new User();
		
		String query = "SELECT * FROM usertable WHERE uname = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, uName);
		ResultSet res = preparedStatement.executeQuery();
		if (res.next()) {
			p = queryGetPerson(res.getString("uName"));
			u = new User(p, res.getString("uName"), res.getString("pWord"), res.getString("User_Role"));
		}
		
		return u;
	}
	
	public Person queryGetPerson(String uName) throws SQLException {
		
		Person p = new Person();
		
		String query = "SELECT * FROM person WHERE uname = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, uName);
		ResultSet res = preparedStatement.executeQuery();
		if (res.next()) {
			p = new Person(res.getString("fName"), res.getString("lName"));
		}
		
		return p;
	}
	
	public Boolean queryCreateUser(User u) throws SQLException {
		String query = "insert into usertable (uName, pWord, User_Role) values (?, ?, ?)";
		PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, u.getUser());
		preparedStatement.setString(2, u.getPass());
		preparedStatement.setString(3, u.getRole());
		int count = preparedStatement.executeUpdate();
		if (count > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public Boolean queryCreatePerson(User u) throws SQLException {
		String query = "insert into person (fName, lName, uName) values (?, ?, ?)";
		PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
		String name = u.getPerson().getName();
		String[] fullName = name.split(" ");
		preparedStatement.setString(1, fullName[0]);
		preparedStatement.setString(2, fullName[1]);
		preparedStatement.setString(3, u.getUser());
		int count = preparedStatement.executeUpdate();
		if (count > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public Boolean queryDeletePerson(User u) throws SQLException {
		String query = "DELETE FROM person WHERE uname = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, u.getUser());
		int count = preparedStatement.executeUpdate();
		if (count > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public Boolean queryDeleteUser(User u) throws SQLException {
		String query = "DELETE FROM usertable WHERE uname = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, u.getUser());
		int count = preparedStatement.executeUpdate();
		if (count > 0) {
			return true;
		}
		else {
			return false;
		}
	}
}
