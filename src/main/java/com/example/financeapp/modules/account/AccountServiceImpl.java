package com.example.financeapp.modules.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountServiceImpl implements AccountService {
    
    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<Account> findAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        List<Account> accountResponses = new ArrayList<>();
        accounts.forEach(account -> accountResponses.add(account));
        return accountResponses;
    }

    @Override
    public Account findMyAccount(String email) {
        Account account = accountRepository.findByEmail(email);
        return account;
    }

}
