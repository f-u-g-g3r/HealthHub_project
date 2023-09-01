package com.artjomkuznetsov.healthhub.security.auth;

import java.util.Objects;

public class AuthenticationResponse {
    private String token;
    private Boolean isEmailTaken;

    public AuthenticationResponse(String token) {
        if (token.equals("Email is taken")) {
            this.isEmailTaken = true;
        } else {
            this.token = token;
            this.isEmailTaken = false;
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getEmailTaken() {
        return isEmailTaken;
    }

    public void setEmailTaken(Boolean emailTaken) {
        isEmailTaken = emailTaken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticationResponse that = (AuthenticationResponse) o;
        return Objects.equals(token, that.token) && Objects.equals(isEmailTaken, that.isEmailTaken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, isEmailTaken);
    }

    @Override
    public String toString() {
        return "AuthenticationResponse{" +
                "token='" + token + '\'' +
                ", isEmailTaken=" + isEmailTaken +
                '}';
    }
}
