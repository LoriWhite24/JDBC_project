package com.cognixia.jump.jdbc.project;

import java.sql.Date;
import java.util.List;

import com.cognixia.jump.jdbc.project.StudentInfo.Standing;

public interface StudentDAO {

	public List<Student> getAllStudents();
	
	public Student getStudentById(int id);
	
	public List<Student> getStudentByDeptName(String name);
	
	public List<Student> getStudentByStanding(Standing standing);
	
	public Student getStudent(String firstName, String lastName, String gender, Date dob, int credits);
	
	public Student addStudent(Student student);
	
	public boolean updateStudent(Student student);
	
	public boolean deleteStudent(int id);
	
}
