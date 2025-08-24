package com.aurionpro.model;

public class User {
	private int userId;
	private String username;
	private String password;
	private String role;
	private String firstName;
	private String lastName;
	private String email;
	private String accountId;

	// Existing constructors and getters/setters...
	public User(int userId, String username, String password, String role, String firstName, String lastName,
			String email, String accountId) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.role = role;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.accountId = accountId;
	}

	// New constructor for creating a user
	public User(String username, String password, String role, String firstName, String lastName, String email) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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
	
	public String getAccountId() { 
		return accountId; 
		}
    public void setAccountId(String accountId) { 
    	this.accountId = accountId; 
    	}
}
