package com.nkxgen.spring.jdbc.DaoInterfaces;

import com.nkxgen.spring.jdbc.model.UserCredentials;

public interface UserCredentialsDAO {

	boolean userCredentialsCheck(String username, String password);

	void saveUserCredentials(UserCredentials userCredentials);

}
