package com.artjomkuznetsov.healthhub.repositories;

import com.artjomkuznetsov.healthhub.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUuid(String uuid);
    List<User> findByFamilyDoctorId(Long doctorId);
}
