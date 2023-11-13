package com.artjomkuznetsov.healthhub.security.auth;

import com.artjomkuznetsov.healthhub.models.Role;
import com.artjomkuznetsov.healthhub.models.Status;

public class DoctorAuthenticationResponse {
    private String token;
    private Boolean isEmailTaken;
    private Role role;
    private Status status;
    private Long doctorId;

    public DoctorAuthenticationResponse(String token) {
        this.isEmailTaken = true;
    }

    public DoctorAuthenticationResponse(String token, Long doctorId, Role role) {
        this.token = token;
        this.doctorId = doctorId;
        this.role = role;
        this.isEmailTaken = false;
        this.status = Status.INACTIVE;
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

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
