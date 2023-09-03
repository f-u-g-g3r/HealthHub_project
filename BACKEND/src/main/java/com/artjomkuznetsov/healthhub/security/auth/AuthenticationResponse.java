package com.artjomkuznetsov.healthhub.security.auth;

import java.util.Objects;

public class AuthenticationResponse {
    private String token;
    private Boolean isEmailTaken;
    private Long uid;

    public AuthenticationResponse(String token) {
        this.isEmailTaken = true;
    }

    public AuthenticationResponse(String token, Long uid) {
        this.token = token;
        this.uid = uid;
        this.isEmailTaken = false;
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

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticationResponse that = (AuthenticationResponse) o;
        return Objects.equals(token, that.token) && Objects.equals(isEmailTaken, that.isEmailTaken) && Objects.equals(uid, that.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, isEmailTaken, uid);
    }

    @Override
    public String toString() {
        return "AuthenticationResponse{" +
                "token='" + token + '\'' +
                ", isEmailTaken=" + isEmailTaken +
                ", uid=" + uid +
                '}';
    }
}
