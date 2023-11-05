package com.artjomkuznetsov.healthhub.repositories;

import com.artjomkuznetsov.healthhub.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email);
    Optional<Doctor> findByUuid(String uuid);
}
