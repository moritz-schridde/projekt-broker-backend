package com.example.financeapp.modules.bank;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;



@Entity
public class Bank {
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

    public Bank(@NotNull String iban, String name, String surname, double amount, String bic, String type, long accountId) {
        this.iban = iban;
        this.name = name;
        this.surname = surname;
        this.amount = amount;
        this.bic = bic;
        this.type = type;
        this.accountId = accountId;
    }

    public Bank(@NotNull String iban, double amount) {
        this.iban = iban;
        this.amount = amount;
    }

    public Bank() {

    }

    enum mode{
        WITHDRAW,
        DEPOSIT
    }
}
