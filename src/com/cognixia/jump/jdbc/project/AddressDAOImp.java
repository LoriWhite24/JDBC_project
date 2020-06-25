package com.cognixia.jump.jdbc.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressDAOImp implements AddressDAO {

	private Connection conn = ConnectionManagerProperties.getConnection();

	@Override
	public Address getAddressById(int id) {
		Address address = null;

		try(PreparedStatement pstmt = conn.prepareStatement("select * from address where address_id = ?")) {

			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			if(rs.next()) {
				address = new Address( rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
			}
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return address;
	}

	@Override
	public Address getAddress(String street, String city, String state, String zip) {
		Address address = null;

		try(PreparedStatement pstmt = conn.prepareStatement("select * from address where street = ? and city = ? and state = ? and zip_code = ?")) {

			pstmt.setString(1, street);
			pstmt.setString(2, city);
			pstmt.setString(3, state);
			pstmt.setString(4, zip);

			ResultSet rs = pstmt.executeQuery();

			if(rs.next()) {
				address = new Address( rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
			}
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return address;
	}
	
	@Override
	public boolean updateAddress(Address address) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("update address set street = ?, city = ?, state = ?, zip_code = ? where address_id = ?");
			
			pstmt.setString(1, address.getStreet());
			pstmt.setString(2, address.getCity());
			pstmt.setString(3, address.getState());
			pstmt.setString(4, address.getZip());
			pstmt.setInt(5, address.getId());
			
			int update = pstmt.executeUpdate();
			
			if(update > 0) {
				return true;
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Address addAddress(Address address) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("insert into address values(?,?,?,?,?)");
			
			pstmt.setInt(1, address.getId());
			pstmt.setString(2, address.getStreet());
			pstmt.setString(3, address.getCity());
			pstmt.setString(4, address.getState());
			pstmt.setString(5, address.getZip());
			
			int insert = pstmt.executeUpdate();
			
			if(insert > 0) {
				
				address = getAddress(address.getStreet(), address.getCity(), address.getState(), address.getZip());
				return address;
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
