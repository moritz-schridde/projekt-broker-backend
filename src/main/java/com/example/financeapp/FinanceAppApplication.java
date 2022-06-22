package com.example.financeapp;


import com.example.financeapp.modules.bank.Bank;
import com.example.financeapp.modules.bank.BankRepository;

import com.example.financeapp.modules.depot.Depot;
import com.example.financeapp.modules.depot.DepotRepository;
import com.example.financeapp.modules.orderbook.Order;
import com.example.financeapp.modules.orderbook.OrderRepository;
import com.example.financeapp.modules.share.Share;
import com.example.financeapp.modules.share.ShareRepository;
import com.example.financeapp.modules.depot.DepotShareAmount;
import com.example.financeapp.modules.depot.DepotShareAmountRepository;
import com.example.financeapp.modules.user.User;
import com.example.financeapp.modules.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Timestamp;

@SpringBootApplication
public class FinanceAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinanceAppApplication.class, args);

    }

    @Bean
    CommandLineRunner init(ShareRepository shareRepository, UserRepository userRepository,
                           DepotRepository depotRepository, DepotShareAmountRepository depoShareAmountRepository, OrderRepository orderRepository) {
        return args -> {
            Share s1 = new Share("SAP", "WKN", (byte) 1, 100.0, "tech");
            shareRepository.save(s1);
            Share s2 = new Share("BASF", "WKN", (byte) 2, 50.0, "chemie");
            shareRepository.save(s2);
            User buyer = new User("buyer", "surname", "email@mail.com", 12345,
                    "street", "12", "12345", "city", "country",
                    "1", "2", "1995" );
            Depot buyerDepot = new Depot();
            buyerDepot.setTotalCash(1000);
            buyer.setMyDepot(buyerDepot);
            DepotShareAmount dsa = new DepotShareAmount();
            dsa.setDepot(buyerDepot);
            dsa.setShare(s1);
            dsa.setAmount(15);
            depotRepository.save(buyerDepot);
            userRepository.save(buyer);
            depoShareAmountRepository.save(dsa);


            User seller = new User("seller", "melk", "email2@mail.com", 12345,
                    "street", "12", "12345", "city", "country",
                    "1", "2", "1995" );
            Depot sellerDepot = new Depot();
            sellerDepot.setTotalCash(1000);
            seller.setMyDepot(sellerDepot);
            DepotShareAmount bdsa = new DepotShareAmount();
            bdsa.setDepot(sellerDepot);
            bdsa.setShare(s1);
            bdsa.setAmount(10);
            depotRepository.save(sellerDepot);
            userRepository.save(seller);
            depoShareAmountRepository.save(bdsa);


            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            Order buyOrder = new Order(1,3,5,  Order.State.OPEN,  Order.OfferType.BUY, Order.OrderType.MARKETORDER, 0);
            Order buyOrder2 = new Order(1,3,5,  Order.State.OPEN,  Order.OfferType.BUY, Order.OrderType.MARKETORDER, 0);
            Order sellOrder  = new Order(1,5,10,  Order.State.OPEN,  Order.OfferType.SELL, Order.OrderType.MARKETORDER, 0);

            orderRepository.save(buyOrder);
            orderRepository.save(buyOrder2);
            orderRepository.save(sellOrder);



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
