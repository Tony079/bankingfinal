package com.nkxgen.spring.jdbc.events;

public class UserCredentialsEvent {

	private String password;
	private String username;

	public UserCredentialsEvent(String password, String username) {
		this.password = password; // Assign the value of the 'event' parameter to the 'event' member variable of the
									// class
		this.username = username; // Assign the value of the 'username' parameter to the 'username' member variable of
									// the class
	}

	public String getPassword() {
		return password; // Return the value of the 'event' member variable
	}

	public String getUsername() {
		return username; // Return the value of the 'username' member variable
	}

}
