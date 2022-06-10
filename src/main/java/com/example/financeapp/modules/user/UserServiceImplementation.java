package com.example.financeapp.modules.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
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
    public String toString() {
        return super.toString();
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
