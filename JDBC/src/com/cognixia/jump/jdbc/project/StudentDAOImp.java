package com.cognixia.jump.jdbc.project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImp implements StudentDAO {

	private Connection conn = ConnectionManagerProperties.getConnection();
	private AddressDAOImp addDAO = new AddressDAOImp();
	private DepartmentDAOImp depDAO = new DepartmentDAOImp();

	@Override
	public List<Student> getAllStudents() {
		List<Student> studentList = new ArrayList<Student>();
		
		try(Statement stmt = conn.createStatement()) { 
			ResultSet rs = stmt.executeQuery("select * from student"); 
			
			while(rs.next()) {
				// add to list
				studentList.add(new Student(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getInt(6), addDAO.getAddressById(rs.getInt(7)), depDAO.getDepartmentByID(rs.getInt(8))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return studentList;
	}

	@Override
	public Student getStudentById() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addStudent(Student student) {
		
		// insert into student values(id, fname, lname, gender, dob, credits, addr_id, dept_id);
		
		// grab the id for the department
		int deptId = student.getDept().getId();
		
		// grab the id for the address
		int addrId = student.getAddress().getId();
		
		// add in your values to statement
		
		
		return false;
	}

	@Override
	public boolean updateStudent(Student student) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteStudent(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
