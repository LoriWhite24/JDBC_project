package com.cognixia.jump.jdbc.project;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cognixia.jump.jdbc.project.StudentInfo.Standing;

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
	public List<Student> getStudentByDeptName(String name) {
		List<Student> studentList = new ArrayList<Student>();
		
		Department dept = depDAO.getDepartmentByName(name);
		
		try(PreparedStatement pstmt = conn.prepareStatement("select * from student where dept_id = ?")) { 
			
			pstmt.setInt(1,  dept.getId());
			
			ResultSet rs = pstmt.executeQuery(); 
			
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
	public List<Student> getStudentByStanding(Standing standing) {
		List<Student> studentList = new ArrayList<Student>();
		
		try(PreparedStatement pstmt = conn.prepareStatement("select * from student where credits between ? and ?")) { 
			
			pstmt.setInt(1, standing.minCredits);
			pstmt.setInt(2, standing.maxCredits);
			
			ResultSet rs = pstmt.executeQuery(); 
			
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
	public Student getStudent(String firstName, String lastName, String gender, Date dob, int credits) {

		Student student = null;

		// select * from student where student_id = ?
		try(PreparedStatement pstmt = conn.prepareStatement("select * from student where first_name = ? and last_name = ? and gender = ? and date_of_birth = ? and credits = ?")) {

			pstmt.setString(1, firstName);
			pstmt.setString(2, lastName);
			pstmt.setString(3, gender);
			pstmt.setDate(4, dob);
			pstmt.setInt(5, credits);

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
				student = getStudent(student.getFirstName(), student.getLastName(), student.getGender(), student.getDob(), student.getCredits());
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
		
		boolean del = false;
		try(PreparedStatement pstmt = conn.prepareStatement("delete from registration where student_id = ?")) {

			pstmt.setInt(1, id);

			int deleted = pstmt.executeUpdate();

			if(deleted > 0) {
				del = true;
			}
			pstmt.close();
			
		} catch(SQLException e) {
			
			e.printStackTrace();
		}

		if(del) {
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
		}
		

		return false;
	}

}
