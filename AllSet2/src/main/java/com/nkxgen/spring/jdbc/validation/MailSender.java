package com.nkxgen.spring.jdbc.validation;

import com.nkxgen.spring.jdbc.model.BankUser;

public interface MailSender {

	void sendAccountDataModifiedEmail(String recipientEmail, String username);

	void sendPasswordUpdateEmail(String to_user);

	String sendOTP(String to_user);

	void userAdded(BankUser bankUser, String userID);

}
