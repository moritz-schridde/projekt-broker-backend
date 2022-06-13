package com.example.financeapp.modules.depot.responses;

import lombok.Getter;
import lombok.Setter;
public class DepotResponse {
    @Getter
    @Setter
    private String shareName;

    @Getter
    @Setter
    private double currentShareValue;

    @Getter
    @Setter
    private String iconId;
}
