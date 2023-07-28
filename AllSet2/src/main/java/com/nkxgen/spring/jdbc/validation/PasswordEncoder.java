package com.nkxgen.spring.jdbc.validation;

public interface PasswordEncoder {
	
	boolean checkPassword(String password, String hashedPassword);

	String hashPassword(String password);
}
