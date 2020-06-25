package com.cognixia.jump.jdbc.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class DepartmentDAOImp implements DepartmentDAO {
	
	// connection attribute to make access to db easier
	private Connection conn = ConnectionManagerProperties.getConnection();

	@Override
	public List<Department> getAllDepartments() {
		
		List<Department> deptList = new ArrayList<>();
		
		// find all departments
		try(Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select * from department"); ) {
			
			while(rs.next()) {
				
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String phone = rs.getString(3);
				
				// add to list
				Department dept = new Department(id, name, phone);
				deptList.add(dept);
				
			}
			
			stmt.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		return deptList;
	}

	@Override
	public Department getDepartmentByID(int id) {
		
		Department dept = null;
		
		// select * from department where dept_id = ?
		try(PreparedStatement pstmt = conn.prepareStatement("select * from department where dept_id = ?")) {
			
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				int deptId = rs.getInt(1);
				String name = rs.getString(2);
				String phone = rs.getString(3);
				
				dept = new Department(deptId, name, phone);
				
			}
			
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return dept;
	}

	@Override
	public Department getDepartmentByName(String name) {
		Department dept = null;

		// select * from department where dept_name = ?
		try (PreparedStatement pstmt = conn.prepareStatement("select * from department where dept_name = ?")) {

			pstmt.setString(1, name);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				int deptId = rs.getInt(1);
				String deptName = rs.getString(2);
				String phone = rs.getString(3);

				dept = new Department(deptId, deptName, phone);

			}
			
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dept;
	}

	@Override
	public Department addDepartment(Department dept) {
		
		if(getDepartmentByName(dept.getName()) != null) {
			updateDepartment(getDepartmentByName(dept.getName()));
			return getDepartmentByName(dept.getName());
		} else {
			try {
				PreparedStatement pstmt = conn.prepareStatement("insert into department values(?,?,?)");

				pstmt.setInt(1, dept.getId());
				pstmt.setString(2, dept.getName());
				pstmt.setString(3, dept.getPhone());

				int insert = pstmt.executeUpdate();

				if(insert > 0) {
					dept = getDepartmentByName(dept.getName());
					return dept;
				}

				pstmt.close();

			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	//Not used still needs fixing
	@Override
	public boolean deleteDepartment(int id) {
		Department dept = getDepartmentByID(id);
		Set<Integer> courseIds = new TreeSet<Integer>();
		Set<Integer> courseTermIds = new TreeSet<Integer>();
		
		try(PreparedStatement pstmt = conn.prepareStatement("select course_id from course where dept_id = ?")) {

			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			if(rs.next()) {
				courseIds.add(rs.getInt("course_id"));
			}
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		try(PreparedStatement pstmt = conn.prepareStatement("select course_term_id from course_term where course_id = ?")) {

			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			if(rs.next()) {
				courseTermIds.add(rs.getInt("course_term_id"));
			}
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		try(PreparedStatement pstmt = conn.prepareStatement("delete from department where dept_id = ?")) {
			
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

	@Override
	public boolean updateDepartment(Department dept) {
		
		// update department set dept_name = ?, dept_phone = ? where dept_id = ?
		try(PreparedStatement pstmt = conn.prepareStatement("update department set dept_name = ?, dept_phone = ? where dept_id = ?");) {
			
			pstmt.setString(1, dept.getName());
			pstmt.setString(2, dept.getPhone());
			
			pstmt.setInt(3, dept.getId());
			
			int update = pstmt.executeUpdate();
			
			if(update > 0) {
				return true;
			}
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean updateDepartmentName(int id, String name) {
		
		// update department set dept_name = ? where dept_id = ?
		try(PreparedStatement pstmt = conn.prepareStatement("update department set dept_name = ? where dept_id = ?")) {
			
			pstmt.setString(1, name);
			pstmt.setInt(2, id);
			
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

}
