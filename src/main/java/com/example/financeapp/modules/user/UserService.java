package com.example.financeapp.modules.user;

import java.util.List;
import java.util.UUID;


public interface UserService {

    User getUser(UUID id);

    String toString();
    List<User> findAllUsers();
}
