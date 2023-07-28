package com.nkxgen.spring.jdbc.validation;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderImpl implements PasswordEncoder{

    public  String hashPassword(String password) {
        // Generate a random salt and hash the password
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public  boolean checkPassword(String password, String hashedPassword) {
        // Check if the password matches the hashed password
        return BCrypt.checkpw(password, hashedPassword);
    }

    
}

