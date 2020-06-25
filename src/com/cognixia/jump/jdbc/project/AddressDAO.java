package com.cognixia.jump.jdbc.project;

public interface AddressDAO {

	public Address getAddressById(int id);
	
	public Address getAddress(String street, String city, String state, String zip);
	
	public boolean updateAddress(Address address);
	
	public Address addAddress(Address address);
	
}
