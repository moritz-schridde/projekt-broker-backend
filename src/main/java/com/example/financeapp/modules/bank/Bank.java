package com.example.financeapp.modules.bank;

import com.example.financeapp.modules.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Bank {

    @Getter
    @Setter
    @ManyToOne
    @JsonIgnore
    private User user;
    @Getter
    @Setter
    @Id
    @NotNull
    private String iban;

    @Getter
    @Setter
    private String surname;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String bic;

    @Getter
    @Setter
    private double amount;

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private long accountId;

    public Bank(@NotNull String iban, @NotNull User user , String name, String surname, double amount, String bic, String type) {
        this.iban = iban;
        this.name = name;
        this.surname = surname;
        this.amount = amount;
        this.bic = bic;
        this.type = type;
        this.user = user;
    }


    public Bank() {

    }

    enum mode {
        WITHDRAW,
        DEPOSIT
    }
}
