package com.example.financeapp.modules.orderbook.communication.models;

import com.example.financeapp.modules.orderbook.OrderEnums;
import lombok.Getter;
import lombok.Setter;

public class OrderCommunicationModel {

    @Getter
    @Setter
    private long orderId;

    @Getter
    @Setter
    private OrderEnums.OfferType offerType;

    @Getter
    @Setter
    private OrderEnums.OrderType orderType;

    @Getter
    @Setter
    private OrderEnums.State state;

    @Getter
    @Setter
    // Iso normed date string
    private String timestamp;

    @Getter
    @Setter
    private OrderInfoModel info;

    public OrderCommunicationModel() {

    }
 }
