package org.interviews.doodle.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("User with id:" + id +" not found");
    }

    public UserNotFoundException(String email) { super("The email "+ email +" was not found");
    }
}
