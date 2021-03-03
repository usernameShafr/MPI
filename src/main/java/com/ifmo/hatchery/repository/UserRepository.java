package com.ifmo.hatchery.repository;

import com.ifmo.hatchery.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository<T extends User, ID extends Long> extends JpaRepository<User, Long> {
    User findByUsername(String username);
}