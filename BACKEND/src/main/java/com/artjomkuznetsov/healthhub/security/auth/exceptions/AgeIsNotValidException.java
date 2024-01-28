package com.artjomkuznetsov.healthhub.security.auth.exceptions;

public class AgeIsNotValidException extends RuntimeException {
    public AgeIsNotValidException() {
        super("Age is not valid");
    }
}
