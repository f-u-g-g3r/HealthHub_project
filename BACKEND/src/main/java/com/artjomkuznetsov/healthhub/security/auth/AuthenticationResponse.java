package com.artjomkuznetsov.healthhub.security.auth;

import com.artjomkuznetsov.healthhub.models.Role;

import java.util.Objects;

public class AuthenticationResponse {
    private String token;
    private boolean isEmailTaken;
    private boolean isAgeValid;
    private Role role;
    private Long uid;
    private Long medCardId;

    public AuthenticationResponse(String message) {
        if (message.equals("Email is taken") ) {
            this.isEmailTaken = true;
        } else if (message.equals("Age is not valid")) {
            this.isAgeValid = false;
        }

    }

    public AuthenticationResponse(String token, Long uid, Long medCardId, Role role) {
        this.token = token;
        this.uid = uid;
        this.role = role;
        this.medCardId = medCardId;
        this.isEmailTaken = false;
        this.isAgeValid = true;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean isEmailTaken() {
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

    public Long getMedCardId() {
        return medCardId;
    }

    public void setMedCardId(Long medCardId) {
        this.medCardId = medCardId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isAgeValid() {
        return isAgeValid;
    }

    public void setAgeValid(boolean ageValid) {
        isAgeValid = ageValid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticationResponse that = (AuthenticationResponse) o;
        return isEmailTaken == that.isEmailTaken && isAgeValid == that.isAgeValid && Objects.equals(token, that.token) && role == that.role && Objects.equals(uid, that.uid) && Objects.equals(medCardId, that.medCardId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, isEmailTaken, isAgeValid, role, uid, medCardId);
    }

    @Override
    public String toString() {
        return "AuthenticationResponse{" +
                "token='" + token + '\'' +
                ", isEmailTaken=" + isEmailTaken +
                ", isAgeValid=" + isAgeValid +
                ", role=" + role +
                ", uid=" + uid +
                ", medCardId=" + medCardId +
                '}';
    }
}
