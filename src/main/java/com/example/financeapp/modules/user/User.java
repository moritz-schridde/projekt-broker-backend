package com.example.financeapp.modules.user;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

public class User {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    @NotNull
    private UUID id;

    @Getter
    @Setter
    @NotNull
    private String forename;

    @Getter
    @Setter
    @NotNull
    private String surname;

    @Getter
    @Setter
    @NotNull
    private String password;

    @Getter
    @Setter
    @NotNull
    private String email;

    // Object for address?

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

    public User() {
    }

    public User(@NotNull UUID id, @NotNull String forename, @NotNull String surname, @NotNull String password, @NotNull String email) {
        this.id = id;
        this.forename = forename;
        this.surname = surname;
        this.password = passwordEncoder().encode(password);
        this.email = email;
    }

//    public User(@NotNull String username, @NotNull String password, @NotNull String email) {
//        this.username = username;
//        this.password = passwordEncoder().encode(password);
//        this.email = email;
//    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
