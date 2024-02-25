package com.artjomkuznetsov.healthhub.repositories;

import com.artjomkuznetsov.healthhub.models.Doctor;
import com.artjomkuznetsov.healthhub.models.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email);
    Optional<Doctor> findByUuid(String uuid);

    Optional<List<Doctor>> findByFirstname(String firstname);

    Optional<List<Doctor>> findByLastname(String lastname);

    List<Doctor> findAllByStatus(Status status);

    Page<Doctor> findAllByStatus(Status status, PageRequest pageRequest);
}
