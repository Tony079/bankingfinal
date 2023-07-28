package com.nkxgen.spring.jdbc.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.nkxgen.spring.jdbc.DaoInterfaces.UserCredentialsDAO;
import com.nkxgen.spring.jdbc.events.UserCredentialsEvent;
import com.nkxgen.spring.jdbc.model.UserCredentials;
import com.nkxgen.spring.jdbc.validation.PasswordEncoder;

@Component
public class UserCredentialsListener {

	@Autowired
	private UserCredentialsDAO userCredentialsDAO;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@EventListener
	public void CreationEvent(UserCredentialsEvent event) {
		UserCredentials userCredentials = new UserCredentials();
		String hashpwd = passwordEncoder.hashPassword(event.getPassword());
		userCredentials.setUsername(event.getUsername());
		userCredentials.setPassword(hashpwd);
		System.out.println(event.getPassword() + " " + hashpwd);
		userCredentialsDAO.saveUserCredentials(userCredentials);
	}

}
