package com.example.financeapp.modules.user;

import com.example.financeapp.modules.user.requests.UserCreateRequest;

import java.util.UUID;

public interface UserService {

    User getUserByEmail(String email);

    UUID createUser(UserCreateRequest userCreateRequest);

    boolean updateUser(User user, UserUpdateRequest userUpdateRequest);

    boolean deleteUser(User user);
}
