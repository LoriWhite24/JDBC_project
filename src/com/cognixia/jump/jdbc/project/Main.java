package com.cognixia.jump.jdbc.project;

import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		
		StudentDAO studentDao = new StudentDAOImp();
		
		char answer;
		Scanner in = new Scanner(System.in);
		
		do {
			int choice = 0;
			do {
				System.out.println("How would you like to manage students?"
						+ "\n1: Retrieve all students"
						+ "\n2: Retrieve student by ID"
						+ "\n3: Update student information"
						+ "\n4: Delete Student"
						+ "\n5: Add new student");
				choice = Integer.parseInt(in.nextLine().trim());
			}while(choice > 5 || choice < 1);
			

			switch(choice) {
				case 1:
					System.out.println("STUDENTS\n--------------------------");
					studentDao.getAllStudents().stream().forEach(System.out::println);
					break;
				case 2:
					System.out.println("Please enter a Student ID:\n");
					int id = Integer.parseInt(in.nextLine().trim());

					System.out.println("Retrieve student by ID\n--------------------------");
					Student student = studentDao.getStudentById(id);
					System.out.println(student);
					break;
				case 3:
					System.out.println("Update student information\n--------------------------");
					break;
				case 4:
					System.out.println("SENIOR\n--------------------------");
					break;
			}
			System.out.println("Do you wish to continue then type y: ");
			answer = in.nextLine().trim().toLowerCase().charAt(0);
		}while(answer == 'y');
		in.close();
		System.out.println("Bye.");
	}
}
