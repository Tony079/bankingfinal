package com.nkxgen.spring.jdbc.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.nkxgen.spring.jdbc.DaoInterfaces.UserCredentialsDAO;
import com.nkxgen.spring.jdbc.events.UserCredentialsEvent;
import com.nkxgen.spring.jdbc.model.UserCredentials;

@Component
public class UserCredentialsListener {

	@Autowired
	private UserCredentialsDAO userCredentialsDAO;

	@EventListener
	public void CreationEvent(UserCredentialsEvent event) {
		UserCredentials userCredentials = new UserCredentials();

		userCredentials.setUsername(event.getUsername());
		userCredentials.setPassword(event.getPassword());
		userCredentialsDAO.saveUserCredentials(userCredentials);
	}

}
