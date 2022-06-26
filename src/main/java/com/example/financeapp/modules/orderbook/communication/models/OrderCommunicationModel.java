package com.example.financeapp.modules.orderbook.communication.models;

import com.example.financeapp.modules.orderbook.Order;
import com.example.financeapp.modules.share.Share;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

public class OrderCommunicationModel {

    @Getter
    @Setter
    private long orderId;

    @Getter
    @Setter
    private Order.OfferType offerType;

    @Getter
    @Setter
    private Order.OrderType orderType;

    @Getter
    @Setter
    private Order.State state;

    @Getter
    @Setter
    @NotNull
    // Iso normed date string
    private String timestamp;

    @Getter
    @Setter
    private OrderInfoModel info;


    public OrderCommunicationModel() {

    }



    public OrderCommunicationModel(Order o, Share s){
        OrderInfoModel model = new OrderInfoModel();
        model.setCount(o.getCount());
        model.setShare(s);
        model.setValue(o.getMaxMinPreis());

        this.orderId=o.getId();
        this.offerType= o.getOfferType();
        this.orderType=o.getOrderType();
        this.state=o.getState();
        this.timestamp= o.getTimestamp2();
        this.info = model;
    }


 }
