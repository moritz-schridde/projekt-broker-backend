package com.example.financeapp.modules.user;

import com.example.financeapp.modules.user.communication.models.UserCreateCommunicationModel;
import com.example.financeapp.modules.user.communication.models.UserUpdateCommunicationModel;

import java.util.UUID;

public interface UserService {

    User getUserByEmail(String email);

    UUID createUser(UserCreateCommunicationModel userCreateCommunicationModel);

    boolean updateUser(User user, UserUpdateCommunicationModel userUpdateRequest);

    boolean deleteUser(User user);
}
