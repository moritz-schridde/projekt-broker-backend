package com.example.financeapp.modules.orderbook;

import lombok.Getter;
import lombok.Setter;

public class OrderCreateRequest {
    @Getter
    @Setter
    private long shareId;

    @Getter
    @Setter
    private int count;

    @Getter
    @Setter
    private OrderEnums.OfferType offerType;

    @Getter
    @Setter
    private OrderEnums.OrderType orderType;

    @Getter
    @Setter
    double price; //nur wenn keine Marketorder
}
