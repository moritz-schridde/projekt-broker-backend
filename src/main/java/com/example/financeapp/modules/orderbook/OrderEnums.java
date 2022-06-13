package com.example.financeapp.modules.orderbook;

public class OrderEnums {
    enum OfferType{
        BUY, SELL
    }
    enum OrderType{
        MARKETORDER, LIMITORDER, STOPORDER
    }
    enum State{
        OPEN, CLOSED, PENDING
    }
}
