package com.example.financeapp.modules.share.account;

import java.util.List;

public interface AccountService {

    List<Account> findAllAccounts();
    Account findMyAccount(String email);
}
