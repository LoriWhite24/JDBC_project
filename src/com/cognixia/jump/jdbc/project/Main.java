package com.cognixia.jump.jdbc.project;

public class Main {
	
	public static void main(String[] args) {
		
		DepartmentDAO deptDao = new DepartmentDAOImp();
		
		System.out.println("Get all departments");
		
		for( Department dept : deptDao.getAllDepartments() ) {
			System.out.println(dept);
		}
		

			
		StudentDAO studentDao = new StudentDAOImp();

		System.out.println("Get all students");

		for( Student student : studentDao.getAllStudents() ) {
			System.out.println(student);

		}
		
		
		
//		Department newDept = new Department(0, "Film", "1234567800");
//		
//		boolean added = deptDao.addDepartment(newDept);
//		
//		if(added) {
//			System.out.println("Added new department");
//		}
		
//		Department toUpdate = deptDao.getAllDepartments().get(0);
//		
//		toUpdate.setPhone("2121212121");
//		toUpdate.setName("Accounting");
//		
//		boolean updated = deptDao.updateDepartment(toUpdate);
//		
//		if(updated) {
//			System.out.println("Updated department");
//		}
		
		
//		boolean updated = deptDao.updateDepartmentName(10019, "Film Studies");
//		
//		if(updated) {
//			System.out.println("Updated department");
//		}
		
		
//		Department dept = deptDao.getDepartmentByID(10000);
//		
//		System.out.println("\nDepartment grabbed: " + dept);
//		
//		Department dept2 = deptDao.getDepartmentByName("Film Studies");
//		
//		System.out.println("\nDepartment grabbed: " + dept2);
		
		
		boolean deleted = deptDao.deleteDepartment(10020);
		
		if(deleted) {
			System.out.println("department deleted");
		}
		
		
		
	}

}
