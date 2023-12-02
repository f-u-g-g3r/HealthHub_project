package com.artjomkuznetsov.healthhub.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Could not find user " + id);
    }

    public UserNotFoundException(String uuid) { super("Could not find user by uuid " + uuid); }
}
