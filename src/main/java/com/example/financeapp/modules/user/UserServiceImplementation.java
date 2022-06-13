package com.example.financeapp.modules.user;

import com.example.financeapp.modules.depot.Depot;
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
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public UUID createUser(UserCreateRequest userCreateRequest) {
        User user = new User();
        userMapping(user, userCreateRequest.getName(), userCreateRequest.getSurname(), userCreateRequest.getPhoneNumber(),
                userCreateRequest.getStreet(), userCreateRequest.getHouseNumber(), userCreateRequest.getPostalCode(),
                userCreateRequest.getCity(), userCreateRequest.getCountry(), userCreateRequest.getBirthDay(),
                userCreateRequest.getBirthMonth(), userCreateRequest.getBirthYear());
        user.setEmail(userCreateRequest.getEmail());
        user.setMyDepot(new Depot());
        if (!userRepository.existsById(user.getId())) {
            userRepository.save(user);
            return user.getId();
        } else {
            return null;
        }

    }

    private void userMapping(User user, String name, String surname, int phoneNumber, String street, String houseNumber,
                             String postalCode, String city, String country, int birthDay, int birthMonth, int birthYear) {
        if (name != null) user.setName(name);
        if (surname != null) user.setSurname(surname);
        if (phoneNumber != 0) user.setPhoneNumber(phoneNumber);
        if (street != null) user.setStreet(street);
        if (houseNumber != null) user.setHouseNumber(houseNumber);
        if (postalCode != null) user.setPostalCode(postalCode);
        if (city != null) user.setCity(city);
        if (country != null) user.setCountry(country);
        if (birthDay != 0) user.setBirthDay(birthDay);
        if (birthMonth != 0) user.setBirthMonth(birthMonth);
        if (birthYear != 0) user.setBirthYear(birthYear);
    }

    @Override
    public boolean updateUser(User user, UserUpdateRequest userUpdateRequest) {
        if (userRepository.existsById(user.getId())) {
            userMapping(user, userUpdateRequest.getName(), userUpdateRequest.getSurname(),
                    userUpdateRequest.getPhoneNumber(), userUpdateRequest.getStreet(),
                    userUpdateRequest.getHouseNumber(), userUpdateRequest.getPostalCode(), userUpdateRequest.getCity(),
                    userUpdateRequest.getCountry(), userUpdateRequest.getBirthDay(), userUpdateRequest.getBirthMonth(),
                    userUpdateRequest.getBirthYear());
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteUser(User user) {
        if (userRepository.existsById(user.getId())) {
            userRepository.deleteById(user.getId());
            return true;
        }
        return false;
    }

}
