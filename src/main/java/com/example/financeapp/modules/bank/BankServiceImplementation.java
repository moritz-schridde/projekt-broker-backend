package com.example.financeapp.modules.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class BankServiceImplementation implements BankService {

    private final BankRepository bankRepository;

    @Autowired
    public BankServiceImplementation(BankRepository bankRepository){
        this.bankRepository = bankRepository;
    }

    @Override
    public Bank changeAmount(String iban, double amount, Bank.mode mode) throws Exception {

        int sign = (mode == Bank.mode.WITHDRAW ? 1 : -1);

        Bank bank2save = bankRepository.getBankByIban(iban);
        bank2save.setAmount(amount * sign + bank2save.getAmount());
        bankRepository.save(bank2save);

        return bank2save;
    }

    @Override
    public Bank getBank(String iban){
        return bankRepository.getBankByIban(iban);
    }

}
