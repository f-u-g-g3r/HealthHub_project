package com.artjomkuznetsov.healthhub.security.auth;

import com.artjomkuznetsov.healthhub.models.Role;
import com.artjomkuznetsov.healthhub.models.Status;

import java.util.Objects;

public class DoctorAuthenticationResponse {
    private String token;
    private boolean isEmailTaken;
    private boolean isAgeValid;
    private Role role;
    private Status status;
    private Long doctorId;

    public DoctorAuthenticationResponse(String message) {
        if (message.equals("Email is taken")) {
            this.isEmailTaken = true;
        } else if (message.equals("Age is not valid")) {
            this.isAgeValid = false;
        }
    }

    public DoctorAuthenticationResponse(String token, Long doctorId, Role role) {
        this.token = token;
        this.doctorId = doctorId;
        this.role = role;
        this.isEmailTaken = false;
        this.status = Status.INACTIVE;
    }public DoctorAuthenticationResponse(String token, Long doctorId, Role role, Status status) {
        this.token = token;
        this.doctorId = doctorId;
        this.role = role;
        this.isEmailTaken = false;
        this.status = status;
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
        DoctorAuthenticationResponse that = (DoctorAuthenticationResponse) o;
        return isEmailTaken == that.isEmailTaken && isAgeValid == that.isAgeValid && Objects.equals(token, that.token) && role == that.role && status == that.status && Objects.equals(doctorId, that.doctorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, isEmailTaken, isAgeValid, role, status, doctorId);
    }

    @Override
    public String toString() {
        return "DoctorAuthenticationResponse{" +
                "token='" + token + '\'' +
                ", isEmailTaken=" + isEmailTaken +
                ", isAgeValid=" + isAgeValid +
                ", role=" + role +
                ", status=" + status +
                ", doctorId=" + doctorId +
                '}';
    }
}
