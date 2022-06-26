package com.example.financeapp.modules.bank.models;

import com.example.financeapp.modules.bank.Bank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankRequestCommunicationModelCreate {
    private String kontoId;
    private double amount;
    private String name;
    private String surname;
    private String bic;
    private String type;
}

