package com.example.financeapp.modules.orderbook.requests;

import com.example.financeapp.modules.orderbook.OrderEnums;
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
    double price;
}
