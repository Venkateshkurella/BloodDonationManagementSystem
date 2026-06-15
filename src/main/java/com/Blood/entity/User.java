package com.Blood.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String mobileNumber;
	private String Name;
	private String bloodgroup;
	private String location;
	private String email;

	public int getId() {
		return id;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public String getName() {
		return Name;
	}
	public String getBloodgroup() {
		return bloodgroup;
	}
	public String getLocation() {
		return location;
	}
	public String getEmail() {
		return email;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public void setName(String name) {
		Name = name;
	}
	public void setbloodgroup(String bloodgroup) {
		this.bloodgroup = bloodgroup;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public User(int id, String mobileNumber, String name, String bloodgroup, String location, String email) {
		super();
		this.id = id;
		this.mobileNumber = mobileNumber;
		Name = name;
		this.bloodgroup = bloodgroup;
		this.location = location;
		this.email = email;
	}
	
	public User() {
		super();
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", mobileNumber=" + mobileNumber + ", Name=" + Name + ", bloodgroup=" + bloodgroup
				+ ", location=" + location + ", email=" + email + "]";
	}
}
