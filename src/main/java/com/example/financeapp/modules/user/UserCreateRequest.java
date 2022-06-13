package com.example.financeapp.modules.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NotNull
public class UserCreateRequest {

    private String email;
    private String name;
    private String surname;
    private int phoneNumber;
    private String street;
    private String houseNumber;
    private String postalCode;
    private String city;
    private String country;
    private int birthDay;
    private int birthMonth;
    private int birthYear;
}
