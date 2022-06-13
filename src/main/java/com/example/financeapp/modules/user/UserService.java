package com.example.financeapp.modules.user;

import java.util.UUID;

public interface UserService {

    User getUser(UUID id);

    UUID createUser(User user);

    boolean updateUser(UUID id, User user);

    boolean deleteUser(UUID userId);
}
