package com.example.financeapp.modules.bank;

import java.util.List;
import java.util.Map;

public interface BankService {
    List<Bank> getBank() throws Exception;

    Boolean changeAmount(String iban, double amount, Bank.mode mode) throws Exception;

    Boolean create(Map<String, Object> body) throws Exception;

}
