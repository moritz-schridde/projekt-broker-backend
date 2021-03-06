package com.example.financeapp.modules.user.communication.models;

import com.example.financeapp.modules.user.User;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NotNull
public class UserCreateCommunicationModel {

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
