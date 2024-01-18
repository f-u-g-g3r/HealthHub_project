package com.artjomkuznetsov.healthhub.models;


import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "doctors")
public class Doctor implements UserDetails {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String firstname;
    private String lastname;
    private String dateOfBirth;
    private String gender;
    private String address;
    private String email;
    private String phone;
    private String password;
    private String specialization;
    private String placeOfWork;
    private String licenseNumber;
    private String licenseIssuingDate;
    private String licenseIssuingAuthority;
    private String uuid;
    private Long calendarId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Doctor() {
        this.status = Status.INACTIVE;
        this.role = Role.DOCTOR;
    }

    public Doctor(String firstname, String lastname, String dateOfBirth, String gender, String address, String email, String phone, String password, String specialization, String placeOfWork, String licenseNumber, String licenseIssuingDate, String licenseIssuingAuthority, String uuid, Long calendarId) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.specialization = specialization;
        this.placeOfWork = placeOfWork;
        this.licenseNumber = licenseNumber;
        this.licenseIssuingDate = licenseIssuingDate;
        this.licenseIssuingAuthority = licenseIssuingAuthority;
        this.uuid = uuid;
        this.status = Status.INACTIVE;
        this.calendarId = calendarId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getPlaceOfWork() {
        return placeOfWork;
    }

    public void setPlaceOfWork(String placeOfWork) {
        this.placeOfWork = placeOfWork;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseIssuingDate() {
        return licenseIssuingDate;
    }

    public void setLicenseIssuingDate(String licenseIssuingDate) {
        this.licenseIssuingDate = licenseIssuingDate;
    }

    public String getLicenseIssuingAuthority() {
        return licenseIssuingAuthority;
    }

    public void setLicenseIssuingAuthority(String licenseIssuingAuthority) {
        this.licenseIssuingAuthority = licenseIssuingAuthority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(Long calendarId) {
        this.calendarId = calendarId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(id, doctor.id) && Objects.equals(firstname, doctor.firstname) && Objects.equals(lastname, doctor.lastname) && Objects.equals(dateOfBirth, doctor.dateOfBirth) && Objects.equals(gender, doctor.gender) && Objects.equals(address, doctor.address) && Objects.equals(email, doctor.email) && Objects.equals(phone, doctor.phone) && Objects.equals(password, doctor.password) && Objects.equals(specialization, doctor.specialization) && Objects.equals(placeOfWork, doctor.placeOfWork) && Objects.equals(licenseNumber, doctor.licenseNumber) && Objects.equals(licenseIssuingDate, doctor.licenseIssuingDate) && Objects.equals(licenseIssuingAuthority, doctor.licenseIssuingAuthority) && Objects.equals(uuid, doctor.uuid) && Objects.equals(calendarId, doctor.calendarId) && status == doctor.status && role == doctor.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, dateOfBirth, gender, address, email, phone, password, specialization, placeOfWork, licenseNumber, licenseIssuingDate, licenseIssuingAuthority, uuid, calendarId, status, role);
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", specialization='" + specialization + '\'' +
                ", placeOfWork='" + placeOfWork + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", licenseIssuingDate='" + licenseIssuingDate + '\'' +
                ", licenseIssuingAuthority='" + licenseIssuingAuthority + '\'' +
                ", uuid='" + uuid + '\'' +
                ", calendarId=" + calendarId +
                ", status=" + status +
                ", role=" + role +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
