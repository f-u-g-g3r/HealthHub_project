package com.artjomkuznetsov.healthhub.security.auth;

import com.artjomkuznetsov.healthhub.models.Role;

public class DoctorAuthenticationResponse {
    private String token;
    private Boolean isEmailTaken;
    private Role role;
    private Long uid;

    public DoctorAuthenticationResponse(String token) {
        this.isEmailTaken = true;
    }

    public DoctorAuthenticationResponse(String token, Long uid, Role role) {
        this.token = token;
        this.uid = uid;
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


}
