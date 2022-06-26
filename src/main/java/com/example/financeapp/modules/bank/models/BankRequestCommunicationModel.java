package com.example.financeapp.modules.bank.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankRequestCommunicationModel {
    private String kontoId;
    private double amount;
}

