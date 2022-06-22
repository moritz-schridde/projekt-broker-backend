package com.example.financeapp.modules.orderbook.communication.models;

import com.example.financeapp.modules.share.Share;
import lombok.Getter;
import lombok.Setter;

public class OrderInfoModel {

    private Share share;

    @Getter
    @Setter
    //buy or sell value
    private double value;

    @Getter
    @Setter
    //The amount of how much shares the user wants to buy or sell
    private int count;
}
