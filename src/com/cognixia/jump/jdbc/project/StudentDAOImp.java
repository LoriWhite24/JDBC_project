package com.cognixia.jump.jdbc.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
	public Student getStudentById(int id) {

		Student student = null;

		// select * from student where student_id = ?
		try(PreparedStatement pstmt = conn.prepareStatement("select * from student where student_id = ?")) {

			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			if(rs.next()) {

				student = new Student(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getInt(6), addDAO.getAddressById(rs.getInt(7)), depDAO.getDepartmentByID(rs.getInt(8)));

			}

			pstmt.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}


		return student;
		
	}

	@Override
	public Student addStudent(Student student) {
		
		// insert into student values(id, fname, lname, gender, dob, credits, addr_id, dept_id);
		
		try {
			PreparedStatement pstmt = conn.prepareStatement("insert into student values(?,?,?,?,?,?,?,?)");
			
			pstmt.setInt(1, student.getId());
			pstmt.setString(2, student.getFirstName());
			pstmt.setString(3, student.getLastName());
			pstmt.setString(4, student.getGender());
			pstmt.setDate(5, student.getDob());
			pstmt.setInt(6, student.getCredits());
			pstmt.setInt(7, student.getAddress().getId());
			pstmt.setInt(8,  student.getDept().getId());
			
			
			int insert = pstmt.executeUpdate();
			
			if(insert > 0) {
				return student;
			}
		
		
		pstmt.close();
		
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
		
		return null;
	}

	@Override
	public boolean updateStudent(Student student) {

		// update student set student_name = ? where student_id = ?
		try(PreparedStatement pstmt = conn.prepareStatement("update student set first_name = ?, last_name = ?, gender = ?, date_of_birth = ?, credits = ?, address_id = ?, dept_id = ? where student_id = ?")) {

			pstmt.setString(1, student.getFirstName());
			pstmt.setString(2, student.getLastName());
			pstmt.setString(3, student.getGender());
			pstmt.setDate(4, student.getDob());
			pstmt.setInt(5, student.getCredits());
			pstmt.setInt(6, student.getAddress().getId());
			pstmt.setInt(7, student.getDept().getId());
			pstmt.setInt(8, student.getId());

			int updated = pstmt.executeUpdate();

			if(updated > 0) {
				return true;
			}
			pstmt.close();
			
		} catch(SQLException e) {
			
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean deleteStudent(int id) {


		try(PreparedStatement pstmt = conn.prepareStatement("delete from student where student_id = ?")) {

			pstmt.setInt(1, id);

			int deleted = pstmt.executeUpdate();

			if(deleted > 0) {
				
				return true;
			}
			pstmt.close();
			
		} catch(SQLException e) {
			
			e.printStackTrace();
		}

		return false;
	}

}
