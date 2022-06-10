package com.example.financeapp;


import com.example.financeapp.modules.bank.Bank;
import com.example.financeapp.modules.bank.BankRepository;

import com.example.financeapp.modules.share.Share;
import com.example.financeapp.modules.share.ShareRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FinanceAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinanceAppApplication.class, args);

    }

    @Bean
    CommandLineRunner init(ShareRepository shareRepository
    ) {
        return args -> {
            Share s1 = new Share(424252L,"SAP",  "1", 133.46, "tech");
            shareRepository.save(s1);
            Share s2 = new Share(52352523L,"BASF",  "2", 55.32, "chemie");
            shareRepository.save(s2);
        };
    }

    @Bean
    CommandLineRunner init2(BankRepository bankRepository
    ) {
        return args -> {
            Bank b1 = new Bank("DE89370400440532013000", "Klaus", "Mayer", 25.5, "MANSDE66XXX", "blub", 1);
            bankRepository.save(b1);
        };
    }
}
