package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Singleton
public class DataAccess {
	private static DataAccess instance;
	private Connection conn;

	private DataAccess(String args[]) {
		try {
			String url = args[0];
			String login = args[1];
			String password = args[2];

			/* Get the Connection object. */
			conn = DriverManager.getConnection(url, login, password);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public Connection getConnection() {
		return conn;
	}

	public void close() throws SQLException {
		conn.close();
	}
	
	public static synchronized DataAccess getInstance() {
		if(instance == null) {
			String[] args = {"jdbc:mysql://localhost:3306/rentcar","root","ultrasecurepwd"};
			instance = new DataAccess(args);
		}
		return instance;
	}

}
