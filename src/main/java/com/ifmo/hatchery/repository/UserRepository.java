package com.ifmo.hatchery.repository;

import com.ifmo.hatchery.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}