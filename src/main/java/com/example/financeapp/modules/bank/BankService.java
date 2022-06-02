package com.example.financeapp.modules.bank;

import java.util.List;

public interface BankService {
    Bank getBank(String iban) throws Exception;
    Bank changeAmount(String iban, double amount, Bank.mode mode) throws Exception;

}
