package com.example.financeapp.modules.user;

import com.example.financeapp.modules.user.id.UserId;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.StringJoiner;

/* {
    name : "name",
    surname : "surname",
    email : "email",
    password : "encrypted secret",
    phone: "phoneNumber",
    street : "street",
    number : "houseNumber",
    postalCode : "postalCode",
    city : "city",
    country : "country",
    birthDay : "bDay",
    birthMonth : "bMonth",
    birthYear : "bYear"
} */
public class User {

    @Id
    @GeneratedValue
    @Getter
    @NotNull
    private UserId id;

    @Getter
    @Setter
    @NotNull
    private String name;

    @Getter
    @Setter
    @NotNull
    private String surname;

    @Getter
    @Setter
    @NotNull
    private String email;

    @Getter
    @Setter
    @NotNull
    private String password;

    @Getter
    @Setter
    @NotNull
    private int phoneNumber;

    @Getter
    @Setter
    @NotNull
    private String street;

    @Getter
    @Setter
    @NotNull
    private String houseNumber;

    @Getter
    @Setter
    @NotNull
    private String postalCode;

    @Getter
    @Setter
    @NotNull
    private String city;

    @Getter
    @Setter
    @NotNull
    private String country;

    @Getter
    @Setter
    @NotNull
    private int birthDay;

    @Getter
    @Setter
    @NotNull
    private int birthMonth;

    @Getter
    @Setter
    @NotNull
    private int birthYear;

    @JsonCreator
    public User(@JsonProperty("userId") @NotNull UserId id, @JsonProperty("name") @NotNull String name, @JsonProperty("surname") @NotNull String surname, @JsonProperty("email") @NotNull String email, @JsonProperty("password") @NotNull String password, @JsonProperty("phoneNumber") @NotNull int phoneNumber, @JsonProperty("street") @NotNull String street, @JsonProperty("houseNumber") @NotNull String houseNumber, @JsonProperty("postalCode") @NotNull String postalCode, @JsonProperty("city") @NotNull String city, @JsonProperty("country") @NotNull String country, @JsonProperty("birthDay") @NotNull String birthDay, @JsonProperty("birthMonth") @NotNull String birthMonth, @JsonProperty("birthYear") @NotNull String birthYear) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = passwordEncoder().encode(password);
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.birthDay = Integer.parseInt(birthDay);
        this.birthMonth = Integer.parseInt(birthMonth);
        this.birthYear = Integer.parseInt(birthYear);
    }


    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add(String.format("id=%s", id))
                .toString();
    }

}
