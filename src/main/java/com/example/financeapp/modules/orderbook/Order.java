package com.example.financeapp.modules.orderbook;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;

@Entity
@Table(name="Orders")
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
    private int count;

    @Getter
    @Setter
    private Timestamp timestamp;



    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('OPEN', 'CLOSED')")
    @NotNull
    private State state;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('BUY', 'SELL')")
    @NotNull
    private OfferType offerType;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('MARKETORDER', 'LIMITORDER','STOPORDER')")  //Limit für buy stop für sell
    @NotNull
    private  OrderType orderType;

    @Getter
    @Setter
    double maxMinPreis; //nur wenn keine Marketorder

    @Getter
    @Setter
    public static boolean reloadOrderbook= true;

    @Getter
    @Setter
    public static ArrayList<Order> orderBook;

    @Getter
    @Setter
    public double agreedPrice;

    @Getter
    @Setter
    public Timestamp orderClosedAt;

    @Getter
    @Setter
    public int alreadySoldOrBought;


    public Order(long id, long shareId, long depotId, int count, Timestamp timestamp, State state,
                 OfferType offerType, OrderType orderType, double maxMinPreis) {
        this.id = id;
        this.shareId = shareId;
        this.depotId = depotId;
        this.count = count;
        this.timestamp = timestamp;
        this.state = state;
        this.offerType = offerType;
        this.orderType = orderType;
        this.maxMinPreis = maxMinPreis;
    }

    public Order(long id, long shareId, long depotId, int count, Timestamp timestamp, double maxMinPreis) {
        this.id = id;
        this.shareId = shareId;
        this.depotId = depotId;
        this.count = count;
        this.timestamp = timestamp;
        this.maxMinPreis = maxMinPreis;
    }

    public Order(long shareId, long depotId, int count, Timestamp timestamp, State state, OfferType offerType,
                 OrderType orderType, double maxMinPreis) {
        this.shareId = shareId;
        this.depotId = depotId;
        this.count = count;
        this.timestamp = timestamp;
        this.state = state;
        this.offerType = offerType;
        this.orderType = orderType;
        this.maxMinPreis = maxMinPreis;
    }

    public Order(long shareId, long depotId, int count, State state, OfferType offerType, OrderType orderType,
                 double maxMinPreis) {
        this.shareId = shareId;
        this.depotId = depotId;
        this.count = count;
        this.state = state;
        this.offerType = offerType;
        this.orderType = orderType;
        this.maxMinPreis = maxMinPreis;
        this.alreadySoldOrBought=0;
    }

    public Order (){};

    public enum State{
        OPEN, CLOSED
    }
    public enum OrderType{
        MARKETORDER, LIMITORDER, STOPORDER
    }
    public enum OfferType{
        BUY, SELL
    }

    @Enumerated(EnumType.STRING)
    public State getState(){
        return state;
    }

    @Enumerated(EnumType.STRING)
    public void setState(State state){
        this.state = state;
    }

    @Enumerated(EnumType.STRING)
    public OrderType getOrderType(){
        return orderType;
    }

    @Enumerated(EnumType.STRING)
    public void setOrderType(OrderType orderType){
        this.orderType = orderType;
    }

    @Enumerated(EnumType.STRING)
    public OfferType getOfferType(){
        return offerType;
    }

    @Enumerated(EnumType.STRING)
    public void setOfferType(OfferType offerType){
        this.offerType = offerType;
    }
}
