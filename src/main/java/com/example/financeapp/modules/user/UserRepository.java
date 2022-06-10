package com.example.financeapp.modules.user;

import com.example.financeapp.modules.user.id.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User getUserById(UUID id);
    List<User> findAll();
}
