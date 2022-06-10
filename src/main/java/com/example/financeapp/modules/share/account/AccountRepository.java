package com.example.financeapp.modules.share.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUsername(String username);
    Account findByEmail(String email);
    Account findById(String id);
}
