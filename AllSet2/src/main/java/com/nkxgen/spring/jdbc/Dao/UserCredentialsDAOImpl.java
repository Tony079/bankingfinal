package com.nkxgen.spring.jdbc.Dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nkxgen.spring.jdbc.DaoInterfaces.UserCredentialsDAO;
import com.nkxgen.spring.jdbc.Exception.UsernameNotFoundException;
import com.nkxgen.spring.jdbc.Exception.WrongPasswordException;
import com.nkxgen.spring.jdbc.model.UserCredentials;
import com.nkxgen.spring.jdbc.validation.PasswordEncoder;

@Component
public class UserCredentialsDAOImpl implements UserCredentialsDAO {

	@PersistenceContext
	private EntityManager entityManager;
	Logger LOGGER = LoggerFactory.getLogger(UserCredentialsDAOImpl.class);

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	public boolean userCredentialsCheck(String username, String password)
			throws UsernameNotFoundException, WrongPasswordException {
		LOGGER.info("Checking user credentials for username: {}", username);

		String queryString = "SELECT uc FROM UserCredentials uc WHERE uc.username = :username ";
		TypedQuery<UserCredentials> query = entityManager.createQuery(queryString, UserCredentials.class);
		query.setParameter("username", username);

		int count = query.getResultList().size();

		if (count == 0) {
			String errorMessage = "User not found";
			LOGGER.error(errorMessage);
			throw new UsernameNotFoundException(errorMessage);
		} else if (count == 1) {

			UserCredentials user = query.getSingleResult();

			if (passwordEncoder.checkPassword(password, user.getPassword())) {
				return true;
			} else {
				String errorMessage = "Wrong password";
				LOGGER.error(errorMessage);
				throw new WrongPasswordException(errorMessage);
			}
		} else if (count > 0) {
			String errorMessage = "Wrong password";
			LOGGER.error(errorMessage);
			throw new WrongPasswordException(errorMessage);
		} else {
			String errorMessage = "Error occurred while checking user credentials";
			LOGGER.error(errorMessage);
			throw new RuntimeException(errorMessage);
		}

	}

	@Transactional
	public void saveUserCredentials(UserCredentials userCredentials) {
		try {
			entityManager.persist(userCredentials);
			entityManager.flush();
			// The userCredentials object will now be saved to the database
		} catch (Exception e) {
			// Handle the exception if needed
			e.printStackTrace();
			throw new RuntimeException("Error occurred while saving user credentials");
		}
	}

	@Transactional
	public void updateUserCredentials(UserCredentials userCredentials) {
		try {
			entityManager.merge(userCredentials);
			entityManager.flush();
			// The userCredentials object will now be saved to the database
		} catch (Exception e) {
			// Handle the exception if needed
			e.printStackTrace();
			throw new RuntimeException("Error occurred while Updating user credentials");
		}
	}
}
