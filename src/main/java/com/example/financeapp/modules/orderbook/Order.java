package com.example.financeapp.modules.orderbook;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.ArrayList;

@Entity
public class Order {

    //attributes
    @Getter
    @Setter
    @Id
    @GeneratedValue
    @NotNull
    private long id;

    @Getter
    @Setter
    private long shareId;

    @Getter
    @Setter
    private long depotId;

    enum State{
        OPEN, CLOSED
    }

    @Getter
    @Setter
    private State state;

    @Getter
    @Setter
    private int count;

    @Getter
    @Setter
    private Timestamp timestamp;

    enum OfferType{
        BUY, SELL
    }

    @Getter
    @Setter
    private OfferType offerType;

    enum OrderType{
        MARKETORDER, LIMITORDER, STOPORDER
    }

    @Getter
    @Setter
    private OrderType orderType;

    @Getter
    @Setter
    double MaxMinPreis; //nur wenn keine Marketorder

    @Getter
    @Setter
    public static boolean reloadOrderbook= true;

    @Getter
    @Setter
    public static ArrayList<Order> orderBook;


}
