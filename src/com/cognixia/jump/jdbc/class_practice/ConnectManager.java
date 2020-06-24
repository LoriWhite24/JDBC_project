package com.cognixia.jump.jdbc.class_practice;
import java.sql.Statement;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectManager {
	
	private static final String URL = "jdbc:mysql://localhost:3306/university";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "milarose24";
	
	public static void main(String[] args) {
		Connection conn = ConnectManager.getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			System.out.println("Statement Created.");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		int count;
		try {
			count = stmt.executeUpdate("insert into student value(null,'Jane','Doe','F','1996-06-24',45,10000,10000);");
			System.out.println("Add rows: " + count);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {
			ResultSet result = stmt.executeQuery("select * from student;");
			
			while(result.next()) {
				int id = result.getInt("student_id");
				String name = result.getString(2) + ", " + result.getString(3);
				Date dob = result.getDate("date_of_birth");
				
				System.out.println("id= " + id + ", name= " + name + ", dob= " + dob);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		try {
			count = stmt.executeUpdate("delete from student where first_name = 'Jane';");
			System.out.println("Delete rows: " + count);
		} catch (SQLException e1) {
			e1.printStackTrace();
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
