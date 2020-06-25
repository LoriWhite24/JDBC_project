package com.cognixia.jump.jdbc.project;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cognixia.jump.jdbc.project.StudentInfo.Standing;

public class Main {
	
	public static void main(String[] args) {
		
		StudentDAO studentDao = new StudentDAOImp();
		AddressDAO addressDao = new AddressDAOImp();
		DepartmentDAO departmenetDao = new DepartmentDAOImp();
		
		int choice = 0;
		boolean invalidInput;
		Scanner in = new Scanner(System.in);
		
		do {			
			do {
				System.out.println("\nHow would you like to manage students?"
						+ "\n1: Retrieve all the Students"
						+ "\n2: Retrieve a Student by their ID"
						+ "\n3: Retrieve all the Students within a Department"
						+ "\n4: Retrieve all the Students with a certian Standing"
						+ "\n5: Update a Student's information"
						+ "\n6: Delete a Student"
						+ "\n7: Add a new Student"
						+ "\n0: Quit");
				choice = Integer.parseInt(in.nextLine().trim());
			}while(choice > 7 || choice < 0);
			
			List<Student> std = null;
			Student student = null;
			Address a = null;
			Department dep = null;
			String fName = null, lName = null, gender = null, dob = null, address = null, name = null, phoneNum = null;
			int cred = 0;
			Date d = null;
			
			switch(choice) {
				case 1:
					System.out.println("\nSTUDENTS\n--------------------------");
					std = studentDao.getAllStudents();
					std.forEach(System.out::println);
					System.out.println("\nTotal: " + std.size());
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
					do {
						System.out.println("Please enter a Department Name:");
						name = in.nextLine().trim();
						
						dep = departmenetDao.getDepartmentByName(name);
						if(dep == null) {
							System.out.println("There isn't a Department with the name" + name + ".");
						}
					}while(dep == null);
					System.out.println("\nSTUDENTS IN DEPARTMENT " + name.toUpperCase() + "\n--------------------------");
					std = studentDao.getStudentByDeptName(name);
					std.forEach(System.out::println);
					System.out.println("\nTotal: " + std.size());
					break;
				case 4:
					int stChoice = 0;
					do {
						do {
							System.out.println("Which class standing would you like to view? "
									+ "\n1: Freshman"
									+ "\n2: Sophmore"
									+ "\n3: Junior"
									+ "\n4: Senior"
									+ "\n5: Super Senior"
									+ "\n6: Advanced Senior"
									+ "\n0: Quit");
							stChoice = Integer.parseInt(in.nextLine().trim());
						}while(stChoice > 6 || stChoice < 0);

							switch(stChoice) {
							case 1:
								System.out.println("FRESHMAN\n--------------------------");
								std = studentDao.getStudentByStanding(Standing.FRESHMAN);
								break;
							case 2:
								System.out.println("SOPHOMORE\n--------------------------");
								std = studentDao.getStudentByStanding(Standing.SOPHMORE);
								break;
							case 3:
								System.out.println("JUNIOR\n--------------------------");
								std = studentDao.getStudentByStanding(Standing.JUNIOR);
								break;
							case 4:
								System.out.println("SENIOR\n--------------------------");
								std = studentDao.getStudentByStanding(Standing.SENIOR);
								break;
							case 5:
								System.out.println("SENIOR\n--------------------------");
								std = studentDao.getStudentByStanding(Standing.SUPER_SENIOR);
								break;
							case 6:
								System.out.println("SENIOR\n--------------------------");
								std = studentDao.getStudentByStanding(Standing.ADVANCED_SENIOR);
								break;
							}
							std.forEach(System.out::println);
							System.out.println("\nTotal: " + std.size());
						}while(stChoice != 0);
					break;
				case 5:
					System.out.println("\nUpdate a Student's information\n--------------------------");
					do {
						System.out.println("Please enter a Student ID:");
						int id = Integer.parseInt(in.nextLine().trim());
						
						student = studentDao.getStudentById(id);
						if(student == null) {
							System.out.println("There isn't a student with ID " + id + ".");
						}
					}while(student == null);
					int sChoice;
					do {
						do {
							System.out.println("\nWhat info of this student would you like to update?"
									+ "\n1: First Name"
									+ "\n2: Last Name"
									+ "\n3: Gender"
									+ "\n4: Date of Birth"
									+ "\n5: Credits"
									+ "\n6: Address"
									+ "\n7: Department"
									+ "\n0: Quit");
							sChoice = Integer.parseInt(in.nextLine().trim());
						}while(sChoice > 7 || sChoice < 0);
						
						switch(sChoice) {
							case 1:
								System.out.println("What is the student's new first name?");
								fName = in.nextLine().trim();
								student.setFirstName(fName);
								break;
							case 2:
								System.out.println("What is the student's new last name?");
								lName = in.nextLine().trim();
								student.setLastName(lName);
								break;
							case 3:
								System.out.println("What is the student's gender?");
								gender = in.nextLine().trim();
								student.setGender(gender);
								break;
							case 4:
								
								do {
									invalidInput = false;
									System.out.println("What is the student's date of birth? (yyyy-MM-dd)");
									dob = in.nextLine().trim();
									
									try {
										d = Date.valueOf(dob);
									} catch(IllegalArgumentException e) {
										invalidInput = true;
										System.out.println("This date is invalid.");
									}
								} while(invalidInput);
								student.setDob(d);
								break;
							case 5:
								System.out.println("What are the student's credits?");
								cred = Integer.parseInt(in.nextLine().trim());
								student.setCredits(cred);
								break;
							case 6:
								
								do {
									invalidInput = false;
									System.out.println("What is the student's address? (123 street name city, ST 12345)");
									address = in.nextLine().trim();
									Pattern pStreet = Pattern.compile("\\d+\\s[a-zA-z]+\\s[a-zA-z]+");
									Matcher mStreet = pStreet.matcher(address);
									mStreet.find();
									Pattern pCity = Pattern.compile("[a-zA-Z]+,");
									Matcher mCity = pCity.matcher(address);
									mCity.find();
									Pattern pState = Pattern.compile("\\s[a-zA-Z]{2}\\s");
									Matcher mState = pState.matcher(address);
									mState.find();
									Pattern pZip = Pattern.compile("\\d{5}");
									Matcher mZip = pZip.matcher(address);
									mZip.find();

									a = student.getAddress();
									try {
										a.setStreet(mStreet.group());
										a.setCity(mCity.group().substring(0, mCity.group().length() - 1));
										a.setState(mState.group().trim());
										a.setZip(mZip.group());
									} catch(IllegalStateException e) {
										invalidInput = true;
										System.out.println("This address is invalid.");
									}
								} while(invalidInput);
								
								addressDao.updateAddress(a);
								student.setAddress(a);
								break;
							case 7:
								int dChoice;
								
								do {
									do {
										System.out.println("\nWhat info of this student's department would you like to update?"
												+ "\n1: Department Name"
												+ "\n2: Department Number"
												+ "\n0: Quit");
										dChoice = Integer.parseInt(in.nextLine().trim());
									}while(dChoice > 2 || dChoice < 0);

									switch(dChoice) {
										case 1:
											System.out.println("Enter the department's name:");
											name = in.nextLine().trim();
											break;
										case 2:
											Matcher mNum;
											do {
												System.out.println("Enter the department's phone number: (0123456789)");
												phoneNum = in.nextLine().trim();
												Pattern pNum = Pattern.compile("\\d{10}");
												mNum = pNum.matcher(phoneNum);
												if(!mNum.matches()) {
													System.out.println("The phone number is invaild.");
												}
											} while(!mNum.matches());
											break;
									}
								} while(dChoice != 0);
								
								dep = student.getDept();
								dep.setName(name);
								dep.setPhone(phoneNum);
								
								departmenetDao.updateDepartment(dep);
								student.setDept(dep);
								break;
						}
					}while(sChoice != 0);				
					
					if(studentDao.updateStudent(student)) {
						System.out.println("\nSTUDENT\n--------------------------");
						System.out.println(student);
					} else {
						System.out.println("Student was not able to be updated.");
					}
					break;
				case 6:
					System.out.println("\nDelete a Student\n--------------------------");
					boolean deleted;
					do {
						System.out.println("Please enter a Student ID:");
						int id = Integer.parseInt(in.nextLine().trim());
						deleted = false;
						
						student = studentDao.getStudentById(id);
						if(student == null) {
							System.out.println("There isn't a student with ID " + id + ".");
						} else {
							deleted = studentDao.deleteStudent(id);
							System.out.println("Student was removed.");
						}
					}while(student == null && !deleted);					
					break;
				case 7:
					System.out.println("\nAdd a new Student\n--------------------------");
					sChoice = 0;
					
					do {
						do {
							System.out.println("\nWhat info of this student would you like to add?"
									+ "\n1: First Name"
									+ "\n2: Last Name"
									+ "\n3: Gender"
									+ "\n4: Date of Birth"
									+ "\n5: Credits"
									+ "\n6: Address"
									+ "\n7: Department"
									+ "\n0: Quit");
							sChoice = Integer.parseInt(in.nextLine().trim());
						}while(sChoice > 7 || sChoice < 0);
						
						switch(sChoice) {
							case 1:
								System.out.println("What is the student's new first name?");
								fName = in.nextLine().trim();
								break;
							case 2:
								System.out.println("What is the student's new last name?");
								lName = in.nextLine().trim();
								break;
							case 3:
								System.out.println("What is the student's gender?");
								gender = in.nextLine().trim();
								break;
							case 4:
								
								do {
									invalidInput = false;
									System.out.println("What is the student's date of birth? (yyyy-MM-dd)");
									dob = in.nextLine().trim();
									
									try {
										d = Date.valueOf(dob);
									} catch(IllegalArgumentException e) {
										invalidInput = true;
										System.out.println("This date is invalid.");
									}
								} while(invalidInput);
								break;
							case 5:
								System.out.println("What are the student's credits?");
								cred = Integer.parseInt(in.nextLine().trim());
								break;
							case 6:
								
								do {
									invalidInput = false;
									System.out.println("What is the student's address? (123 street name city, ST 12345)");
									address = in.nextLine().trim();
									Pattern pStreet = Pattern.compile("\\d+\\s[a-zA-z]+\\s[a-zA-z]+");
									Matcher mStreet = pStreet.matcher(address);
									mStreet.find();
									Pattern pCity = Pattern.compile("[a-zA-Z]+,");
									Matcher mCity = pCity.matcher(address);
									mCity.find();
									Pattern pState = Pattern.compile("\\s[a-zA-Z]{2}\\s");
									Matcher mState = pState.matcher(address);
									mState.find();
									Pattern pZip = Pattern.compile("\\d{5}");
									Matcher mZip = pZip.matcher(address);
									mZip.find();

									try {
										a = new Address(0, mStreet.group(), mCity.group().substring(0, mCity.group().length() - 1), mState.group().trim(), mZip.group());
									} catch(IllegalStateException e) {
										invalidInput = true;
										System.out.println("This address is invalid.");
									}
								} while(invalidInput);
								
								a = addressDao.addAddress(a);
								break;
							case 7:
								int dChoice;
								
								do {
									do {
										System.out.println("\nWhat info of this student's department would you like to update?"
												+ "\n1: Department Name"
												+ "\n2: Department Number"
												+ "\n0: Quit");
										dChoice = Integer.parseInt(in.nextLine().trim());
									}while(dChoice > 2 || dChoice < 0);

									switch(dChoice) {
										case 1:
											System.out.println("Enter the department's name:");
											name = in.nextLine().trim();
											break;
										case 2:
											Matcher mNum;
											do {
												System.out.println("Enter the department's phone number: (0123456789)");
												phoneNum = in.nextLine().trim();
												Pattern pNum = Pattern.compile("\\d{10}");
												mNum = pNum.matcher(phoneNum);
												if(!mNum.matches()) {
													System.out.println("The phone number is invaild.");
												}
											} while(!mNum.matches());
											break;
									}
								} while(dChoice != 0);
								
								dep = new Department(0, name, phoneNum);
								
								dep = departmenetDao.addDepartment(dep);
								break;
						}
					}while(sChoice != 0);				
					
					student = new Student(0, fName, lName, gender, d, cred, a, dep);
					student = studentDao.addStudent(student);
					
					if(student != null) {
						System.out.println("\nSTUDENT\n--------------------------");
						System.out.println(student);
					} else {
						System.out.println("Student was not able to be added.");
					}
					break;
				default:
					System.out.println("\nBye.");
					break;
			}
		}while(choice != 0);
		in.close();
	}
}
