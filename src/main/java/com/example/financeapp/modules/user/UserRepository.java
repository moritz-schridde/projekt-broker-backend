package com.example.financeapp.modules.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    User getUserById(UUID userId);
}
