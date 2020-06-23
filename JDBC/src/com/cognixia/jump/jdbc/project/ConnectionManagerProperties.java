package com.cognixia.jump.jdbc.project;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManagerProperties {
	
	private static Connection connection = null;
	
	
	private static void makeConnection() {
		
		Properties props = new Properties();
		
		try {
			props.load(new FileInputStream("resources/config.properties"));
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		String url = props.getProperty("url");
		String username = props.getProperty("username");
		String password = props.getProperty("password");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			connection = DriverManager.getConnection(url, username, password);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		
		
	}
	
	
	// returning connection
	public static Connection getConnection() {

		if (connection == null) { // if connection not established, create it before returning it
			makeConnection();
		}

		return connection;
	}
	
	
	public static void main(String[] args) {
		
		Connection conn = ConnectionManagerProperties.getConnection();
		System.out.println("Connection made");
		
		try {
			conn.close();
			System.out.println("Closed connection");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
	}

}
