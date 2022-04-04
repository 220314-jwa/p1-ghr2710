package dev.roberts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionFactory {

    private static Connection connection = null;

    // if we make our constructor private, we can implement
    // the singleton design pattern
    public static Connection getConnection() {
        // if we haven't made a connection yet:
        if (connection == null) {
            // import login data from the dbConfig file:
            ResourceBundle bundle = ResourceBundle.getBundle("dbConfig");
            String url = bundle.getString("url");
            String username = bundle.getString("username");
            String password = bundle.getString("password");
            // now, we try connecting to the database:
            try {
                connection = DriverManager.getConnection(url, username, password);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }


}

