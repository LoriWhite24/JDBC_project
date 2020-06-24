package com.cognixia.jump.jdbc.class_practice;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentPreparedStatement {
	
	private static final String URL = "jdbc:mysql://localhost:3306/university";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "milarose24";
	
	public static void main(String[] args) {
		Connection conn = StudentPreparedStatement.getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("select concat(first_name, ' ', last_name) as full_name, credits from student where credits between ? and ? order by full_name;");
			System.out.println("Statement Created.");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		ResultSet result = null;
		char answer;
		Scanner in = new Scanner(System.in);
		
		do {
			int count = 0, choice = 0;
			do {
				System.out.println("Which class standing would you like to view? "
						+ "\n1: Freshman"
						+ "\n2: Sophmore"
						+ "\n3: Junior"
						+ "\n4: Senior");
				choice = Integer.parseInt(in.nextLine().trim());
			}while(choice > 4 || choice < 1);
			
			try {
				switch(choice) {
					case 1:
						System.out.println("FRESHMAN\n--------------------------");
						stmt.setInt(1, 0);
						stmt.setInt(2, 29);
						result = stmt.executeQuery();
						break;
					case 2:
						System.out.println("SOPHOMORE\n--------------------------");
						stmt.setInt(1, 30);
						stmt.setInt(2, 59);
						result = stmt.executeQuery();
						break;
					case 3:
						System.out.println("JUNIOR\n--------------------------");
						stmt.setInt(1, 60);
						stmt.setInt(2, 89);
						result = stmt.executeQuery();
						break;
					case 4:
						System.out.println("SENIOR\n--------------------------");
						stmt.setInt(1, 90);
						stmt.setInt(2, Integer.MAX_VALUE);
						result = stmt.executeQuery();
						break;
				}
				
				String name;
				int credits;
				
				while(result.next()) {
					name = result.getString(1);
					credits = result.getInt(2);
					
					System.out.println("Name: " + name + " Credits: " + credits);
					count++;
				}
				
				System.out.println("\nTotal: " + count);
				
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			
			System.out.println("Do you wish to continue then type y: ");
			answer = in.nextLine().trim().toLowerCase().charAt(0);
		}while(answer == 'y');
		
		System.out.println("Bye.");
		
		try {
			in.close();
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
