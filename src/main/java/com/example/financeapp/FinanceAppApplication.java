package com.example.financeapp;


import com.example.financeapp.modules.bank.Bank;
import com.example.financeapp.modules.bank.BankRepository;

import com.example.financeapp.modules.depot.Depot;
import com.example.financeapp.modules.depot.DepotRepository;
import com.example.financeapp.modules.share.Share;
import com.example.financeapp.modules.share.ShareRepository;
import com.example.financeapp.modules.depot.DepoShareAmount;
import com.example.financeapp.modules.depot.DepoShareAmountRepository;
import com.example.financeapp.modules.user.User;
import com.example.financeapp.modules.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    CommandLineRunner init(ShareRepository shareRepository, UserRepository userRepository,
                           DepotRepository depotRepository, DepoShareAmountRepository depoShareAmountRepository) {
        return args -> {
            Share s1 = new Share(134112L,"SAP", (byte) 1, 133.46, "tech");
            shareRepository.save(s1);
            Share s2 = new Share(1213141L, "BASF", (byte) 2, 55.32, "chemie");
            shareRepository.save(s2);
            User u = new User("name", "surname", "email@mail.com", 12345,
                    "street", "12", "12345", "city", "country",
                    "1", "2", "1995" );
            Depot dp = new Depot();
            u.setMyDepot(dp);
            DepoShareAmount dsa = new DepoShareAmount();
            dsa.setDepot(dp);
            dsa.setShare(s1);
            dsa.setAmount(15);
            depotRepository.save(dp);
            userRepository.save(u);
            depoShareAmountRepository.save(dsa);

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
