package com.example.financeapp.modules.depot.responses;

import lombok.Getter;
import lombok.Setter;
public class DepotResponse {
    @Getter
    @Setter
    private String shareName;

    @Getter
    @Setter
    private String shareId;

    @Getter
    @Setter
    private int shareAmount;

    @Getter
    @Setter
    private double shareValue;

    @Getter
    @Setter
    private String iconId;
}
