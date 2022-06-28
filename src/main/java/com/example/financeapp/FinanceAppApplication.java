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
import org.hibernate.Session;
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
                           DepotRepository depotRepository, DepotShareAmountRepository depoShareAmountRepository, OrderRepository orderRepository, BankRepository bankRepository) {
        return args -> {
            Share s1 = new Share("SAP", "SAP", (byte) 1, 100.0, "tech", "SAP");
            shareRepository.save(s1);
            Share s2 = new Share("BASF", "BASF", (byte) 2, 50.0, "chemie", "BSF");
            shareRepository.save(s2);
            User buyer = new User("buyer", "surname", "niklas@example.com", 12345,
                    "street", "12", "12345", "city", "country", "TAX123TestWerzahltschonSteuern",
                    "01.02.1995" );
            Depot buyerDepot = new Depot();
            buyerDepot.setTotalCash(1450);
            buyerDepot.setTotalNumberShares(10);
            buyer.setMyDepot(buyerDepot);
            DepotShareAmount buyerdsa = new DepotShareAmount();
            buyerdsa.setDepot(buyerDepot);
            buyerdsa.setShare(s1);
            buyerdsa.setAmount(10);
            depotRepository.save(buyerDepot);
            userRepository.save(buyer);
            depoShareAmountRepository.save(buyerdsa);


            User seller = new User("seller", "melk", "davit@example.com", 12345,
                    "street", "12", "12345", "city", "country", "TAX123TestWerzahltschonSteuern",
                    "01.02.1995" );
            Depot sellerDepot = new Depot();
            sellerDepot.setTotalCash(1000);
            sellerDepot.setTotalNumberShares(15);
            seller.setMyDepot(sellerDepot);
            DepotShareAmount sdsa = new DepotShareAmount();
            sdsa.setDepot(sellerDepot);
            sdsa.setShare(s1);
            sdsa.setAmount(15);
            depotRepository.save(sellerDepot);
            userRepository.save(seller);
            depoShareAmountRepository.save(sdsa);


            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            Order buyOrder = new Order(1,3,5,  Order.State.OPEN,  Order.OfferType.BUY, Order.OrderType.MARKETORDER, 0);
            //Order buyOrder2 = new Order(1,3,5,  Order.State.OPEN,  Order.OfferType.BUY, Order.OrderType.MARKETORDER, 0);
            Order sellOrder  = new Order(1,5,15,  Order.State.OPEN,  Order.OfferType.SELL, Order.OrderType.MARKETORDER, 0);
            Order buyOrder3 = new Order(1,3,5,  Order.State.OPEN,  Order.OfferType.BUY, Order.OrderType.LIMITORDER, 90);

            orderRepository.save(buyOrder);
            //orderRepository.save(buyOrder2);
            orderRepository.save(sellOrder);
            orderRepository.save(buyOrder3);

            Bank b1 = new Bank("DE89370400440532013000", seller, "Klaus", "Mayer", 25.5, "MANSDE66XXX", "blub");
            Bank b2 = new Bank("asdfasdfasdf", seller, "Klaus", "Mayer", 25.5, "MANSDE66XXX", "blub");

            bankRepository.save(b1);
            bankRepository.save(b2);



        };
    }

}
