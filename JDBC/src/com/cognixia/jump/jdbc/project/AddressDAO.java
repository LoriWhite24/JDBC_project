package com.cognixia.jump.jdbc.project;

public interface AddressDAO {

	public Address getAddressById(int id);
	
	public boolean updateAddress(Address address);
	
	public boolean addAddress(Address address);
	
}
