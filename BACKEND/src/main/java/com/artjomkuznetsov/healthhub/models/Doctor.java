package com.artjomkuznetsov.healthhub.models;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "doctors")
public class Doctor {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String firstname;
    private String lastname;
    private String dateOfBirth;
    private String gender;
    private String address;
    private String email;
    private String phone;
    private String password;
    private Long medCardID;
    private String specialization;
    private String placeOfWork;
    private String licenseNumber;
    private String licenseIssuingDate;
    private String licenseIssuingAuthority;
    private Status status;

    public Doctor() {}

    public Doctor(String firstname, String lastname, String dateOfBirth, String gender, String address, String email, String phone, String password, Long medCardID, String specialization, String placeOfWork, String licenseNumber, String licenseIssuingDate, String licenseIssuingAuthority) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.medCardID = medCardID;
        this.specialization = specialization;
        this.placeOfWork = placeOfWork;
        this.licenseNumber = licenseNumber;
        this.licenseIssuingDate = licenseIssuingDate;
        this.licenseIssuingAuthority = licenseIssuingAuthority;
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

    public Long getMedCardID() {
        return medCardID;
    }

    public void setMedCardID(Long medCardID) {
        this.medCardID = medCardID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(id, doctor.id) && Objects.equals(firstname, doctor.firstname) && Objects.equals(lastname, doctor.lastname) && Objects.equals(dateOfBirth, doctor.dateOfBirth) && Objects.equals(gender, doctor.gender) && Objects.equals(address, doctor.address) && Objects.equals(email, doctor.email) && Objects.equals(phone, doctor.phone) && Objects.equals(password, doctor.password) && Objects.equals(medCardID, doctor.medCardID) && Objects.equals(specialization, doctor.specialization) && Objects.equals(placeOfWork, doctor.placeOfWork) && Objects.equals(licenseNumber, doctor.licenseNumber) && Objects.equals(licenseIssuingDate, doctor.licenseIssuingDate) && Objects.equals(licenseIssuingAuthority, doctor.licenseIssuingAuthority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, dateOfBirth, gender, address, email, phone, password, medCardID, specialization, placeOfWork, licenseNumber, licenseIssuingDate, licenseIssuingAuthority);
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
                ", medCardID=" + medCardID +
                ", specialization='" + specialization + '\'' +
                ", placeOfWork='" + placeOfWork + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", licenseIssuingDate='" + licenseIssuingDate + '\'' +
                ", licenseIssuingAuthority='" + licenseIssuingAuthority + '\'' +
                '}';
    }
}
