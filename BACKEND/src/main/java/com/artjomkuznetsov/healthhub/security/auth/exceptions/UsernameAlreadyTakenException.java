package com.artjomkuznetsov.healthhub.security.auth.exceptions;

import com.artjomkuznetsov.healthhub.security.auth.AuthenticationResponse;

public class UsernameAlreadyTakenException extends RuntimeException {
    public UsernameAlreadyTakenException (String username) {
        super("Username '" + username + "' has been already taken.");
    }
}
