package com.cognixia.jump.jdbc.project;

public class Temp {
	
	public static void main(String[] args) {
		
		// get in info for student
		String fname = "Orquidia";
		String lname = "Moreno";
		// gender, dob, credits....
		
		// ask student which department they're in
		DepartmentDAO deptDao = new DepartmentDAOImp();
		
		for(Department dept : deptDao.getAllDepartments()) {
			System.out.println(dept);
		}
		
		Department dept = deptDao.getDepartmentByID(10000);
		
		
		int id = 0;
		String street = "this street";
		String city = "a city";
		String state = "NY";
		String zip = "12345";
		
		Address address = new Address(id, street, city, state, zip);
		
		
		
		//Student student = new Student(id, firstName, lastName, gender, dob, credits, address, dept);
		
		
		// 1. Setting up the Address DAO, 1-2 people
		// 2. Split up Student DAO amongst 2-3 people to do methods
		// 3. Work on menu, 2 people
		
		
		
		
		
	}

}
