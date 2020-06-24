package com.cognixia.jump.jdbc.project;

import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		
		StudentDAO studentDao = new StudentDAOImp();
		
		int choice = 0;
		Scanner in = new Scanner(System.in);
		
		do {			
			do {
				System.out.println("\nHow would you like to manage students?"
						+ "\n1: Retrieve all the Students"
						+ "\n2: Retrieve a Student by their ID"
						+ "\n3: Update a Student's information"
						+ "\n4: Delete a Student"
						+ "\n5: Add a new Student"
						+ "\n0: Quit");
				choice = Integer.parseInt(in.nextLine().trim());
			}while(choice > 5 || choice < 0);
			
			Student student = null;

			switch(choice) {
				case 1:
					System.out.println("\nSTUDENTS\n--------------------------");
					studentDao.getAllStudents().forEach(System.out::println);
					break;
				case 2:
					do {
						System.out.println("Please enter a Student ID:");
						int id = Integer.parseInt(in.nextLine().trim());
						
						student = studentDao.getStudentById(id);
						if(student == null) {
							System.out.println("There isn't a student with ID " + id + ".");
						}
					}while(student == null);
					System.out.println("\nSTUDENT\n--------------------------");
					System.out.println(student);
					break;
				case 3:
					System.out.println("\nUpdate a Student's information\n--------------------------");
					break;
				case 4:
					System.out.println("\nDelete a Student\n--------------------------");
					break;
				case 5:
					System.out.println("\nAdd a new Student\n--------------------------");
					break;
				default:
					System.out.println("\nBye.");
					break;
			}
		}while(choice != 0);
		in.close();
	}
}
