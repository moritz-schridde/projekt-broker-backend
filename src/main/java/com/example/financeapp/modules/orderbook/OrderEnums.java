package com.example.financeapp.modules.orderbook;

public class OrderEnums {
    public enum OfferType{
        BUY, SELL
    }
    public enum OrderType{
        MARKETORDER, LIMITORDER, STOPORDER
    }
    public enum State{
        OPEN, CLOSED, PENDING
    }
}
