package com.example.financeapp.modules.bank;

import com.example.financeapp.modules.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankRepository extends JpaRepository<Bank, Long> {
    Bank getBankByIban(String iban);
    List<Bank> getBankByUser(User user);
}
