package com.example.financeapp.modules.user.communication.models;

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
}
