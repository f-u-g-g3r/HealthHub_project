package com.artjomkuznetsov.healthhub.repositories;

import com.artjomkuznetsov.healthhub.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUuid(String uuid);
    Page<User> findByFamilyDoctorId(Long doctorId, PageRequest pageRequest);
    List<User> findByFamilyDoctorId(Long doctorId);
}
