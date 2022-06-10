package com.example.financeapp.modules.orderbook;

import lombok.Getter;
import lombok.Setter;

public class OrderUpdateRequest {
    @Getter
    @Setter
    private long orderId;

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
