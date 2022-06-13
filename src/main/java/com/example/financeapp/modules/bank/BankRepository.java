package com.example.financeapp.modules.bank;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankRepository extends JpaRepository<Bank, Long> {
    Bank getBankByIban(String iban);
}
