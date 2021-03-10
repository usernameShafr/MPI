package com.ifmo.hatchery.repository;

import com.ifmo.hatchery.model.auth.UserX;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository<T extends UserX, ID extends Long> extends JpaRepository<UserX, Long> {
    UserX findByUsername(String username);
}