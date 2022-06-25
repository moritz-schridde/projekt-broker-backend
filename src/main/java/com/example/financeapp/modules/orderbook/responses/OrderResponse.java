package com.example.financeapp.modules.orderbook.responses;

import com.example.financeapp.modules.orderbook.OrderEnums;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

public class OrderResponse {
    @Getter
    @Setter
    private Long orderId;

    @Getter
    @Setter
    private String shareName;

    @Getter
    @Setter
    private double shareValue;

    @Getter
    @Setter
    private double buySellValue;

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
    private OrderEnums.OrderType orderType;

}
