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

    @Getter
    @Setter
    private OrderEnums.State state;

    @Getter
    @Setter
    private int count;

    @Getter
    @Setter
    private Timestamp timestamp;

    @Getter
    @Setter
    private OrderEnums.OfferType offerType;

    @Getter
    @Setter
    private OrderEnums.OrderType orderType;

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
