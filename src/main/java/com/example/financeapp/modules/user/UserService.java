package com.example.financeapp.modules.user;

import com.example.financeapp.modules.user.id.UserId;

import java.util.UUID;

public interface UserService {

    User getUser(UUID id);

    UserId createUser(User user);

    boolean updateUser(UUID id, User user);

    boolean deleteUser(UserId userId);
}
