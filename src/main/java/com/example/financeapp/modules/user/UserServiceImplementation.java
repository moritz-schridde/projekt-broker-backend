package com.example.financeapp.modules.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(UUID id) {
        return userRepository.getUserById(id);
    }

    @Override
    public UUID createUser(User user) {
        if (!userRepository.existsById(user.getId())) {
            UUID id = UUID.randomUUID();
            userRepository.save(new User(id, user));
            return id;
        } else {
            return null;
        }

    }

    @Override
    public boolean updateUser(UUID userId, User user) {
        if (userRepository.existsById(userId)) {
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteUser(UUID userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }
}
