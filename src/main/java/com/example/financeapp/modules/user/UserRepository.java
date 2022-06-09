package com.example.financeapp.modules.user;

import com.example.financeapp.modules.user.id.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User getUserById(UserId id);
}
