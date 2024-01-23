package com.artjomkuznetsov.healthhub.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String uuid;
    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;
    private int age;
    private String gender;
    private String address;
    private String email;
    private String phone;
    private String password;
    private Long medCardID;
    private String token;
    private Long familyDoctorId;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
        this.role = Role.USER;
    }

    public User(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String firstname, String lastname, LocalDate dateOfBirth, String gender, String address, String email, String phone, String password, Long medCardID, String token, Long familyDoctorId, String uuid) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.medCardID = medCardID;
        this.role = Role.USER;
        this.token = token;
        this.familyDoctorId = familyDoctorId;
        this.uuid = uuid;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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
        if (dateOfBirth != null) {
            return dateOfBirth.format(DATE_FORMATTER);
        }
        else {
            return null;
        }
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = LocalDate.parse(dateOfBirth, DATE_FORMATTER);
        LocalDate currentDate = LocalDate.now();
        if (Integer.signum((currentDate.getMonthValue() - this.dateOfBirth.getMonthValue())) == -1 ||
                Integer.signum((currentDate.getDayOfMonth() - this.dateOfBirth.getDayOfMonth())) == -1) {
            this.age = currentDate.getYear() - this.dateOfBirth.getYear() - 1;
        } else {
            this.age = currentDate.getYear() - this.dateOfBirth.getYear();
        }
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

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getMedCardID() {
        return medCardID;
    }

    public void setMedCardID(Long medCardID) {
        this.medCardID = medCardID;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getFamilyDoctorId() {
        return familyDoctorId;
    }

    public void setFamilyDoctorId(Long familyDoctorId) {
        this.familyDoctorId = familyDoctorId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age && Objects.equals(id, user.id) && Objects.equals(uuid, user.uuid) && Objects.equals(firstname, user.firstname) && Objects.equals(lastname, user.lastname) && Objects.equals(dateOfBirth, user.dateOfBirth) && Objects.equals(gender, user.gender) && Objects.equals(address, user.address) && Objects.equals(email, user.email) && Objects.equals(phone, user.phone) && Objects.equals(password, user.password) && Objects.equals(medCardID, user.medCardID) && Objects.equals(token, user.token) && Objects.equals(familyDoctorId, user.familyDoctorId) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, firstname, lastname, dateOfBirth, age, gender, address, email, phone, password, medCardID, token, familyDoctorId, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", medCardID=" + medCardID +
                ", token='" + token + '\'' +
                ", familyDoctorId=" + familyDoctorId +
                ", role=" + role +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    public String getPassword() {
        return password;
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
