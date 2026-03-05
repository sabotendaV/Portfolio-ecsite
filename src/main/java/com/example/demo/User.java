package com.example.demo;

public class User {
	//ユーザー登録
	private long id;
	
	private String lastName;
	
	private String firstName;
	
	private String password;
	
	private String role;
	
	private String email;
	public Long getId() {
		return id;
		}

		public void setId(Long id) {
		this.id = id;
		}

		public String getLastName() {
			return lastName;
		}
		public void setLastName(String Lastname) {
			this.lastName = Lastname;
		}
		public String getFirstName() {
			return firstName;
		}
		
		public void setFirstName(String firstname) {
		this.firstName = firstname;
		}
		public String getPassword() {
			return password;
		}
		public void  setPassword(String password) {
		this.password = password;
		}
		
		public String getRole() {
			return role;
		}
		
		public void setRole(String role) {
		this.role = role;
		}
		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
		this.email = email;
		}
}
