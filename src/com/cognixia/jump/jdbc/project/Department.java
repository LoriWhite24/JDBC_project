package com.cognixia.jump.jdbc.project;

// Model Object/Value Object
// models our department table in our database
// contains constructor and getter/setters for all values
public class Department {
	
	private int id;
	private String name;
	private String phone;
	
	public Department(int id, String name, String phone) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + ", phone=" + phone + "]";
	}

}
