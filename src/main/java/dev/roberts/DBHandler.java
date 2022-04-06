package dev.roberts;
import dev.roberts.Person;
import dev.roberts.User;
import dev.roberts.Story;
import dev.roberts.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Date;


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
	
	public Boolean queryDeleteStory(Story s) throws SQLException {
		String query = "DELETE FROM story WHERE authuser = ? AND title = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, s.getAuthor().getUser());
		preparedStatement.setString(2, s.getTitle());
		int count = preparedStatement.executeUpdate();
		if (count > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public Boolean queryCreateStory(Story s) throws SQLException {
		String query = "insert into story (authuser, eduser, title, genre, slength, excompdate, blurb, description, status) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, s.getAuthor().getUser());
		preparedStatement.setString(2, "Unassigned");
		preparedStatement.setString(3, s.getTitle());
		preparedStatement.setString(4, s.getGenre());
		preparedStatement.setInt(5, s.getLength());
		preparedStatement.setDate(6, s.sqlCompDate());
		preparedStatement.setString(7, s.getBlurb());
		preparedStatement.setString(8, s.getDesc());
		preparedStatement.setString(9, s.getStatus());
		int count = preparedStatement.executeUpdate();
		if (count > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public Boolean queryUpdateStory(Story s) throws SQLException {
		String query = "update story set authuser = ?, eduser = ?, title = ?, genre = ?, slength = ?, excompdate = ?, blurb = ?, description = ?, status = ? where authuser = ? AND title = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, s.getAuthor().getUser());
		preparedStatement.setString(2, s.getEditor().getUser());
		preparedStatement.setString(3, s.getTitle());
		preparedStatement.setString(4, s.getGenre());
		preparedStatement.setInt(5, s.getLength());
		preparedStatement.setDate(6, s.sqlCompDate());
		preparedStatement.setString(7, s.getBlurb());
		preparedStatement.setString(8, s.getDesc());
		preparedStatement.setString(9, s.getStatus());
		preparedStatement.setString(10, s.getAuthor().getUser());
		preparedStatement.setString(11, s.getOldTitle());
		int count = preparedStatement.executeUpdate();
		if (count > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public Story[] queryGetAllStories() throws SQLException {
		Story[] sList;
		String query = "select * from story";
		PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS, ResultSet.TYPE_SCROLL_INSENSITIVE , ResultSet.CONCUR_UPDATABLE);
		ResultSet res = preparedStatement.executeQuery();
		int size = 0;
		if (res != null) {
			res.last();
			size = res.getRow();
		}
		sList = new Story[size];
		res.first();
		int i = 0;
		while (res.next()) {
			String author = res.getString("authuser"); 
			User a = queryGetUser(author);
			String editor = res.getString("eduser"); 
			User e = queryGetUser(editor);
			String title = res.getString("title"); 
			String genre = res.getString("genre"); 
			int len = res.getInt("slength"); 
			java.sql.Date d = res.getDate("excompdate");
			Date day = new Date(d.getTime());
			String blurb = res.getString("blurb"); 
			String desc = res.getString("description");
			String status = res.getString("status"); 
			Story s = new Story(a, e, title, genre, len, day, blurb, desc, status);
			sList[i] = s;
		}
		return sList;
	}
	
	public Story[] queryGetStoriesByAuthor(User u) throws SQLException {
		Story[] sList;
		String query = "select * from story where authuser = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS, ResultSet.TYPE_SCROLL_INSENSITIVE , ResultSet.CONCUR_UPDATABLE);
		preparedStatement.setString(1, u.getUser());
		ResultSet res = preparedStatement.executeQuery();
		int size = 0;
		if (res != null) {
			res.last(); 
			size = res.getRow();
		}
		sList = new Story[size];
		res.first();
		int i = 0;
		while (res.next()) {
			String author = res.getString("authuser"); 
			User a = queryGetUser(author);
			String editor = res.getString("eduser"); 
			User e = queryGetUser(editor);
			String title = res.getString("title"); 
			String genre = res.getString("genre"); 
			int len = res.getInt("slength"); 
			java.sql.Date d = res.getDate("excompdate");
			Date day = new Date(d.getTime());
			String blurb = res.getString("blurb"); 
			String desc = res.getString("description");
			String status = res.getString("status"); 
			Story s = new Story(a, e, title, genre, len, day, blurb, desc, status);
			sList[i] = s;
		}
		return sList;
	}
	
	public Story[] queryGetStoriesByEditor(User u) throws SQLException {
		Story[] sList;
		String query = "select * from story where eduser = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS, ResultSet.TYPE_SCROLL_INSENSITIVE , ResultSet.CONCUR_UPDATABLE);
		preparedStatement.setString(1, u.getUser());
		ResultSet res = preparedStatement.executeQuery();
		int size = 0;
		if (res != null) {
			res.last(); 
			size = res.getRow();
		}
		sList = new Story[size];
		res.first();
		int i = 0;
		while (res.next()) {
			String author = res.getString("authuser"); 
			User a = queryGetUser(author);
			String editor = res.getString("eduser"); 
			User e = queryGetUser(editor);
			String title = res.getString("title"); 
			String genre = res.getString("genre"); 
			int len = res.getInt("slength"); 
			java.sql.Date d = res.getDate("excompdate");
			Date day = new Date(d.getTime());
			String blurb = res.getString("blurb"); 
			String desc = res.getString("description");
			String status = res.getString("status"); 
			Story s = new Story(a, e, title, genre, len, day, blurb, desc, status);
			sList[i] = s;
		}
		return sList;
	}
}
