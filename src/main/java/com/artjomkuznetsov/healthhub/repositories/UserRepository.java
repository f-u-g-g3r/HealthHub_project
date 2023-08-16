package com.artjomkuznetsov.healthhub.repositories;

import com.artjomkuznetsov.healthhub.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
