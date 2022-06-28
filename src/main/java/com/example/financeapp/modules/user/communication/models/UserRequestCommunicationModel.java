package com.example.financeapp.modules.user.communication.models;

import com.example.financeapp.modules.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestCommunicationModel {
    private String email;
    private String name;
    private String surname;
    private int phoneNumber;
    private String street;
    private String houseNumber;
    private String postalCode;
    private String city;
    private String country;
    private String birthDate;
    private String taxNumber;

    public UserRequestCommunicationModel(User user){
        this.email = user.getEmail();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.phoneNumber = user.getPhoneNumber();
        this.street = user.getStreet();
        this.houseNumber = user.getHouseNumber();
        this.postalCode = user.getPostalCode();
        this.city = user.getCity();
        this.country = user.getCountry();
        this.birthDate = user.getBirthDate();
        this.taxNumber = user.getTaxNumber();
    }
}
