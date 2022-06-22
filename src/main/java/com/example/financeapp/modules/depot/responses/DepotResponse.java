package com.example.financeapp.modules.depot.responses;

import lombok.Getter;
import lombok.Setter;
public class DepotResponse {
    @Getter
    @Setter
    private double totalSpendMoney;

    @Getter
    @Setter
    private DepotShareInfoModel[] shares;
}
