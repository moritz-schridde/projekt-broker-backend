package com.example.financeapp.modules.user;

import com.example.financeapp.modules.user.id.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        return userRepository.getUserById(new UserId(id));
    }

    @Override
    public UserId createUser(User user) {
        if (!userRepository.existsById(user.getId().getUUID())) {
            UserId id = new UserId(UUID.randomUUID());
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
    public boolean deleteUser(UserId userId) {
        if (userRepository.existsById(userId.getUUID())) {
            userRepository.deleteById(userId.getUUID());
            return true;
        }
        return false;
    }

}
