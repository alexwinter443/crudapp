package com.app.model;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


/*
 * Alex Vergara
 * 5/1/2022
 */

// associate properties with database table and columns
@Table("users")
public class UserEntity {

	@Id
	@Column("ID")
	Long id;
	
	@Column("USERNAME")
	@Size(min=4, max=30)
	String username;
	
	@Column("PASSWORD")
	String password;
	
	@Column("ROLE")
	String role;
	
	@Column("FIRST_NAME")
	String firstName;
	
	@Column("LAST_NAME")
	String lastName;
	
	@Column("EMAIL")
	String email;
	
	@Column("PHONE")
	String phone;
	
	
	
	// default constructor
	public UserEntity() {
		
	}
	
	// alternate constructor
	public UserEntity(String username, String password) {
		this.username = username;
		this.password = password;
		
	}
	
	// data constructor
	public UserEntity(Long id, String username, String password, String firstName, String lastName, String email, String phone, String role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.role = role;
		
	}
	
	// overrides to String method
	@Override
	public String toString() {
		return "UserEntity [id = " + this.id + ", username = " + this.username + ", password = " + this.password +  ",  role = " + this.role + "]";
	}

	
	// getters and setters
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
