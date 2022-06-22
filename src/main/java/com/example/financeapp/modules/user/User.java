package com.example.financeapp.modules.user;

import com.example.financeapp.modules.depot.Depot;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.StringJoiner;
import java.util.UUID;

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

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue
    @Getter
    @NotNull
    private UUID id;

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
    @Column(unique = true)
    private String email;

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
    private String birthDay;

    @Setter
    @OneToOne
    private Depot myDepot;

    public User() {}

    @JsonCreator
    public User(@JsonProperty("name") @NotNull String name,
                @JsonProperty("surname") @NotNull String surname, @JsonProperty("email") @NotNull String email,
                @JsonProperty("phoneNumber") @NotNull int phoneNumber, @JsonProperty("street") @NotNull String street,
                @JsonProperty("houseNumber") @NotNull String houseNumber,
                @JsonProperty("postalCode") @NotNull String postalCode, @JsonProperty("city") @NotNull String city,
                @JsonProperty("country") @NotNull String country, @JsonProperty("birthDay") @NotNull String birthDay) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.birthDay = birthDay;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add(String.format("id=%s", id))
                .toString();
    }

}
