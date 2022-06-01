package com.example.financeapp.modules.user;

import java.util.UUID;

public interface UserService {

    User getUser(UUID id);

    String toString();
}
