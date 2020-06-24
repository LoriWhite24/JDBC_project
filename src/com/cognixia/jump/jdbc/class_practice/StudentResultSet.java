package com.cognixia.jump.jdbc.class_practice;
import java.sql.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentResultSet {
	
	private static final String URL = "jdbc:mysql://localhost:3306/university";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "milarose24";
	
	public static void main(String[] args) {
		Connection conn = StudentResultSet.getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			System.out.println("Statement Created.");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("STUDENTS\n--------------------------");
		try {
			ResultSet result = stmt.executeQuery("select concat(first_name, ' ', last_name) as full_name, timestampdiff(year, date_of_birth, now()) - (timestampdiff(month, date_of_birth, now()) < 0) - (timestampdiff(day, date_of_birth, now()) < 0) as age from student order by full_name;");
			
			while(result.next()) {
				String name = result.getString(1);
				int age = result.getInt(2);
				
				System.out.println("Name: " + name + " Age: " + age);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		try {
			stmt.close();
			conn.close();
			System.out.println("Closed");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static Connection getConnection() {
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("Connected");
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		
		return conn;
	}
}
